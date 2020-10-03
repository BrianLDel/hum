import React, { useEffect, useState } from 'react';
import API from '../../services/API';
import { Button } from 'react-bootstrap';
import JugadorList from '../../components/Jugador/JugadorList'
import JugadorModal from '../../components/Jugador/JugadorModal';

const Jugador = props => {

  const [jugadores,setJugadores] = useState([]);

  const initialJugadorData = { 
    nombre: '',
    puntos: 0,
    entrenador:{
      id: -1
    }
  }

  const [newJugadorData,setNewJugadorData] = useState(initialJugadorData);

  const [showModal,setShowModal] = useState(false);
  const [isEdit,setIsEdit] = useState(false);
  const [validateForm, setValidateForm] = useState(false);
  const [errorMsg,setErrorMsg] = useState('');

  const [listaEntrenadores, setListaEntrenadores] = useState([]);


  useEffect(() => {
    getJugadores();

    getEntrenadores();
  },[]);

  const getJugadores = async() => {
    try{
      let response = await API.get('/jugadores');
      setJugadores(await calcularGanancias(response));//No basta con que la funcion sea definida async, tambien 
    }                                                 //Tengo que hacer el await acá
    catch(error){
      console.log(error);
    }
  }

  const getEntrenadores = async() => {
    try{
      let response = await API.get('/entrenadores');
      setListaEntrenadores(response);
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

//Hago el request get para las ganancias
const calcularGanancias = async(jugadores) => {
  let response = await API.get('/jugadores/ganancias');

  const jugadoresConGanancias = jugadores.map(jugador => {
    const jugadorGanancia = response.find(jg => jg.idJugador === jugador.id);
    jugador.ganancia = jugadorGanancia.ganancia || 0;
    return jugador;
  })
  return jugadoresConGanancias;
}
  

  const borrarJugador = async(id) => {
    if (window.confirm("Estas seguro?")) {
      try{
        await API.remove(`/jugadores/${id}`);
        getJugadores();
      }
      catch(error){
        console.log(error);
      }
    }
  }

  const recalcularRanking = async(id) =>{
    try{
      await API.update(`/jugadores/${id}/actions/recalculateRanking`);
      getJugadores();
    }
    catch(error){
      console.log(error);
    }
  }

  const agregarJugador = async() => {
    try{
      await API.save('/jugadores', newJugadorData);
      resetModal();
      getJugadores();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const editarJugador = async(id) => {
    try{
      await API.update(`/jugadores/${id}`,newJugadorData);
      resetModal();
      getJugadores();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const handleFormChange = (tipo,value) => {
    if(value === '')
      setValidateForm(true);

      let data = Object.assign({}, newJugadorData);

      switch(tipo){
        case 'nombre':
          data.nombre = value;
          break;
        case 'puntos':
          data.puntos = parseInt(value)
        case 'entrenador':
          data.entrenador.id = parseInt(value);
          break;  
        default:
          break;
      }
    setNewJugadorData(data);
  }  

  const handleFormSubmit = (form, isEdit) => {
    setValidateForm(true);

    if(form.checkValidity())
      isEdit ? editarJugador(newJugadorData.id) : agregarJugador();
  };
 
  const resetModal = () =>{
    setShowModal(false);
    setIsEdit(false);
    setNewJugadorData(initialJugadorData);
    setValidateForm(false);
    setErrorMsg('');
  }

  const handleOpenModal = (editar = false, jugadorToEdit = null) =>{
    if(editar)
    {
      setIsEdit(true);
      setNewJugadorData(jugadorToEdit);
    }
    setShowModal(true);
  }

  const handleCloseModal = () =>{
    resetModal();
  }

  return (
    <div className="container mt-4">
      <h1>Jugadores</h1>
      <Button variant="info mb-3" onClick={()=> handleOpenModal()}> Agregar Jugador </Button> 
      <JugadorModal
        show={showModal}
        handleClose={handleCloseModal}
        handleChange={handleFormChange}
        handleSubmit={handleFormSubmit}
        isEdit={isEdit}
        validate={validateForm}
        errorMsg={errorMsg}
        jugador={newJugadorData}
        listaEntrenadores={listaEntrenadores}
      />
      <JugadorList
        jugadores={jugadores}
        borrarJugador={borrarJugador}
        editarJugador={handleOpenModal}
        recalcularRanking={recalcularRanking}
      />
    </div>
  );
  
}

export default Jugador;