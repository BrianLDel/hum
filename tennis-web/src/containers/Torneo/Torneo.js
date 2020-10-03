import React, { useEffect, useState } from 'react';
import API from '../../services/API';
import { Button } from 'react-bootstrap';
import TorneoList from '../../components/Torneo/TorneoList';
import TorneoModal from '../../components/Torneo/TorneoModal';
import _ from 'lodash';


const Torneo = () => {

  const [torneos, setTorneos] = useState([]); 

  const [showModal,setShowModal] = useState(false);
  const [isEdit,setIsEdit] = useState(false);
  const [validateForm, setValidateForm] = useState(false);
  const [errorMsg,setErrorMsg] = useState('');

  const initialTorneoData = { 
    fechaComienzo: '',
    jugador1: {
      id: -1
    },
    jugador2: {
      id: -1
    },
    jugador3: {
      id: -1
    },
    jugador4: {
      id: -1
    },
    cancha: {
      id: -1
    }
  }
  const [newTorneoData,setNewTorneoData] = useState(initialTorneoData);
  const [listaCanchas,setListaCanchas] = useState([]);
  const [listaJugadores,setListaJugadores] = useState([]);

  useEffect(() => {
    getTorneos();

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

  const getTorneos = async() => {
    try{
      let response = await API.get('/torneos');

      //Mapea la lista de torneos con la fecha formateada DD/MM/YYYY, hh:mm
      let responseFechaFormateada = response.map( torneo =>{
        let formatedDate = new Date(torneo.fechaComienzo).toLocaleDateString('es-AR',dateOptions);
        return torneo = {...torneo,fechaComienzo: formatedDate};
      });
      setTorneos(responseFechaFormateada);
    }
    catch(error){
      console.log(error);
    }
  }

  const borrarTorneo = async(id) => {
    if (window.confirm("Estas seguro?")) {
      try{
        await API.remove(`/torneos/${id}`);
        getTorneos();
      }
      catch(error){
        console.log(error);
      }
    }
  }

  const agregarTorneo = async() => {
    //Copia los valores de 'newTorneoData' en un nuevo objeto 'model'
    let model = Object.assign({}, newTorneoData);

    //Guarda la fecha en formato Date
    model.fechaComienzo = stringToDate(model.fechaComienzo);

    try{
      await API.save('/torneos', model);
      resetModal();
      getTorneos();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const editartorneo = async(id) => {
    //Copia los valores de 'newTorneoData' en un nuevo objeto 'model'
    let model = Object.assign({}, newTorneoData);

    //Guarda la fecha en formato Date
    model.fechaComienzo = stringToDate(model.fechaComienzo);

    try{
      await API.update(`/torneos/${id}`, model);
      resetModal();
      getTorneos();
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

    //Copia los valores de 'newTorneoData' en un nuevo objeto 'data'
    let data = Object.assign({}, newTorneoData);

    switch(tipo){
      case 'jugador1':
        data.jugador1.id = parseInt(value);
        break;
      case 'jugador2':
        data.jugador2.id = parseInt(value);
        break;
      case 'jugador3':
        data.jugador3.id = parseInt(value);
        break;
      case 'jugador4':
        data.jugador4.id = parseInt(value);
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
    setNewTorneoData(data);
  }

  const handleFormSubmit = async(form, isEdit) => {
    setValidateForm(true);
    
    if(!validatetorneo()) return;

    if(form.checkValidity())
      isEdit ? editartorneo(newTorneoData.id) : agregarTorneo();
  };

  const validatetorneo = () => {

    const list = []
    list.push(newTorneoData.jugador1);
    list.push(newTorneoData.jugador2);
    list.push(newTorneoData.jugador3);
    list.push(newTorneoData.jugador4);

    const jugadoresUnicos = _.uniq(list.map(jugador => jugador.id));

    if (jugadoresUnicos.length < 4) {
      setErrorMsg('Los jugadores deben ser únicos.');
      return false;
    }

    const date = stringToDate(newTorneoData.fechaComienzo);

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

  const generarPartidos = async(id) => {

    const torneoAGenerar = torneos.find(torneo => torneo.id === id);
    console.log(torneoAGenerar);

    const list = []

    list.push(torneoAGenerar.jugador1);
    list.push(torneoAGenerar.jugador2);
    list.push(torneoAGenerar.jugador3);
    list.push(torneoAGenerar.jugador4);

    const jugadoresOrdenados = _.orderBy(list, ['puntos'], ['desc']);
    console.log(jugadoresOrdenados);
    const partido1 = { 
      fechaComienzo: stringToDate(torneoAGenerar.fechaComienzo),
      jugadorLocal: {
        id: jugadoresOrdenados[0].id
      },
      jugadorVisitante: {
        id: jugadoresOrdenados[3].id
      },
      cancha: {
        id: torneoAGenerar.cancha.id
      },
      estado: 'NO_INICIADO'
    }

    const partido2 = { 
      fechaComienzo: stringToDate(torneoAGenerar.fechaComienzo),
      jugadorLocal: {
        id: jugadoresOrdenados[1].id
      },
      jugadorVisitante: {
        id: jugadoresOrdenados[2].id
      },
      cancha: {
        id: torneoAGenerar.cancha.id
      },
      estado: 'NO_INICIADO'
    }

    await API.save('/partidos',partido1);
    await API.save('/partidos',partido2);
    alert('Partidos Creados');

  }


  const resetModal = () =>{
    setShowModal(false);
    setIsEdit(false);
    setNewTorneoData(initialTorneoData);
    setValidateForm(false);
    setErrorMsg('');
  }

  const handleOpenModal = (editar = false, torneoToEdit = null) =>{
    if(editar)
    {
      setIsEdit(true);
      setNewTorneoData(torneoToEdit);
    }
    setShowModal(true);
  }

  const handleCloseModal = () =>{
    resetModal();
  }

  return (
    <div className="container mt-4">
      <h1>torneos</h1>
      <Button variant="info mb-3" onClick={()=> handleOpenModal()}>Agregar torneo</Button> 
      <TorneoModal
        show={showModal}
        handleClose={handleCloseModal}
        handleChange={handleFormChange}
        handleSubmit={handleFormSubmit}
        isEdit={isEdit}
        validate={validateForm}
        errorMsg={errorMsg}
        torneo={newTorneoData}
        listaJugadores={listaJugadores}
        listaCanchas={listaCanchas}
      />
      <TorneoList
        torneos={torneos}
        borrarTorneo={borrarTorneo}
        editarTorneo={handleOpenModal}
        generarPartidos={generarPartidos}
      />
    </div>
  );
}

export default Torneo;