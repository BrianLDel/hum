import React, { useEffect, useState } from 'react';
import API from '../../services/API';
import { Button } from 'react-bootstrap';
import PartidoList from '../../components/Partido/PartidoList';
import PartidoModal from '../../components/Partido/PartidoModal';

const Partido = props => {

  const [partidos,setPartidos] = useState([]); 

  const [showModal,setShowModal] = useState(false);
  const [isEdit,setIsEdit] = useState(false);
  const [validateForm, setValidateForm] = useState(false);
  const [errorMsg,setErrorMsg] = useState('');

  const initialPartidoData = { 
    fechaComienzo: '',
    jugadorLocal: {
      id: -1
    },
    jugadorVisitante: {
      id: -1
    },
    cancha: {
      id: -1
    },
    estado: 'NO_INICIADO'
  }
  const [newPartidoData,setNewPartidoData] = useState(initialPartidoData);
  const [listaCanchas,setListaCanchas] = useState([]);
  const [listaJugadores,setListaJugadores] = useState([]);

  useEffect(() => {
    getPartidos();

    //Para cargar la info en los select del modal
    getJugadores();
    getCanchas();
  }, []);

  const dateOptions = {
    year: 'numeric', month: 'numeric', day: 'numeric',
    hour: 'numeric', minute: 'numeric', hour12: false,
  };

  //Usar esta funcion para convertir el string 'fechaComienzo' a Date
  const stringToDate = (dateString) => {
    try{
      const [fecha, hora] = dateString.split(" ");
      const [dd, mm, yyyy] = fecha.split("/");
      if(hora !== undefined){
        if(hora.includes(':')){
          const [hh,mins] = hora.split(":");
          return new Date(yyyy,mm -1, dd,hh,mins);
        }
      }
      return new Date(yyyy,mm -1, dd);
    }
    catch(err){
      console.log(`stringToDate error formateando la fecha: ${err}`);
      return null;
    }
  };

  const getPartidos = async() => {
    try{
      let response = await API.get('/partidos');

      //Mapea la lista de partidos con la fecha formateada DD/MM/YYYY, hh:mm
      let responseFechaFormateada = response.map( partido =>{
        let formatedDate = new Date(partido.fechaComienzo).toLocaleDateString('es-AR',dateOptions);
        return partido = {...partido,fechaComienzo: formatedDate};
      });
      setPartidos(responseFechaFormateada);
    }
    catch(error){
      console.log(error);
    }
  }

  const borrarPartido = async(id) => {
    if (window.confirm("Estas seguro?")) {
      try{
        await API.remove(`/partidos/${id}`);
        getPartidos();
      }
      catch(error){
        console.log(error);
      }
    }
  }

  const iniciarPartido = async(id) => {
    try{
      await API.update(`/partidos/${id}/actions/init`);
    }
    catch(error){
      console.log(error);
    }
  }

  const agregarPartido = async() => {
    //Copia los valores de 'newPartidoData' en un nuevo objeto 'model'
    let model = Object.assign({}, newPartidoData);

    //Guarda la fecha en formato Date
    model.fechaComienzo = stringToDate(model.fechaComienzo);

    //Setea los puntajes en cero
    model.puntosGameActualLocal = 0;
    model.puntosGameActualVisitante = 0;

    try{
      await API.save('/partidos', model);
      resetModal();
      getPartidos();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const editarPartido = async(id) => {
    //Copia los valores de 'newPartidoData' en un nuevo objeto 'model'
    let model = Object.assign({}, newPartidoData);

    //Guarda la fecha en formato Date
    model.fechaComienzo = stringToDate(model.fechaComienzo);

    try{
      await API.update(`/partidos/${id}`, model);
      resetModal();
      getPartidos();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const getJugadores = async() => {
    try{
      let response = await API.get('/jugadores');
      setListaJugadores(response);
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const getCanchas = async() => {
    try{
      let response = await API.get('/canchas');
      setListaCanchas(response)
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  };

  const handleFormChange = (tipo,value) =>{
    if(value === '')
      setValidateForm(true);

    //Copia los valores de 'newPartidoData' en un nuevo objeto 'data'
    let data = Object.assign({}, newPartidoData);

    switch(tipo){
      case 'jugadorLocal':
        data.jugadorLocal.id = parseInt(value);
        break;
      case 'jugadorVisitante':
        data.jugadorVisitante.id = parseInt(value);
        break;
      case 'cancha':
        data.cancha.id = parseInt(value);
        break;
      case 'fechaComienzo':
        data.fechaComienzo = value;
        break;  
      default:
        break;
    }
    setNewPartidoData(data);
  }

  const handleFormSubmit = async(form, isEdit) => {
    setValidateForm(true);
    
    if(!validatePartido()) return;

    let canchaValida = await validateCancha();
    if(!canchaValida) return;
    
    //Si los datos del partido y la cancha fueron validados los envia
    if(form.checkValidity())
      isEdit ? editarPartido(newPartidoData.id) : agregarPartido();
  };

  const validatePartido = () => {

    if (newPartidoData.jugadorLocal.id === newPartidoData.jugadorVisitante.id) {
      setErrorMsg('Los jugadores local y visitante no pueden ser iguales');
      return false;
    }

    const date = stringToDate(newPartidoData.fechaComienzo);

    if (!(date instanceof Date) || isNaN(date.getTime())) {
      setErrorMsg('El formato de la fecha/hora de inicio no es válido');
      return false;
    }
    
    if (date < new Date(Date.now())) {
      setErrorMsg('La fecha/hora de inicio debe ser mayor o igual a la fecha/hora actual');
      return false;
    }

    return true;
  }

  const validateCancha = async() => {

    let partidos = await API.get('/partidos');

    // Define una constante para el intervalo horario de un partido para utilizar la misma cancha de otro
    const INDICE_DIFERENCIA_HORAS = 4;

    let canchaId = newPartidoData.cancha.id;
    let fechaComienzo = stringToDate(newPartidoData.fechaComienzo);

    // Le quita 4 horas a la fecha actual
    let fechaHorasAntes = new Date(new Date(fechaComienzo).setHours(fechaComienzo.getHours() - INDICE_DIFERENCIA_HORAS));

    // Agrega 4 horas a la fecha actual
    let fechaHorasDespues = new Date(fechaComienzo.setHours(fechaComienzo.getHours() + INDICE_DIFERENCIA_HORAS));
    
    // Filtra por los partidos de esa cancha
    let partidosParaCancha = partidos.filter(m => m.cancha.id === canchaId);

    // Si es edición no contempla el partido actual
    if (isEdit)
      partidosParaCancha = partidosParaCancha.filter(m => m.id !== newPartidoData.id);

    // Busca todos los partidos que comiencen en un intervalo de 4 horas antes y 4 horas despues
    let partidosEntreHorarios = partidosParaCancha
      .filter(x =>
        new Date(x.fechaComienzo) > fechaHorasAntes &&
        new Date(x.fechaComienzo) < fechaHorasDespues
      );
      
    if (partidosEntreHorarios.length > 0)
    {
      setErrorMsg('El partido no puede utilizar la misma cancha de otro partido en un intervalo de 4 horas');
      return false;  
    }

    return true;
  }

  const resetModal = () =>{
    setShowModal(false);
    setIsEdit(false);
    setNewPartidoData(initialPartidoData);
    setValidateForm(false);
    setErrorMsg('');
  }

  const handleOpenModal = (editar = false, partidoToEdit = null) =>{
    if(editar)
    {
      setIsEdit(true);
      setNewPartidoData(partidoToEdit);
    }
    setShowModal(true);
  }

  const handleCloseModal = () =>{
    resetModal();
  }

  return (
    <div className="container mt-4">
      <h1>Partidos</h1>
      <Button variant="info mb-3" onClick={()=> handleOpenModal()}>Agregar Partido</Button> 
      <PartidoModal
        show={showModal}
        handleClose={handleCloseModal}
        handleChange={handleFormChange}
        handleSubmit={handleFormSubmit}
        isEdit={isEdit}
        validate={validateForm}
        errorMsg={errorMsg}
        partido={newPartidoData}
        listaJugadores={listaJugadores}
        listaCanchas={listaCanchas}
      />
      <PartidoList
        partidos={partidos}
        borrarPartido={borrarPartido}
        editarPartido={handleOpenModal}
        iniciarPartido={iniciarPartido}
      />
    </div>
  );
  
}

export default Partido;