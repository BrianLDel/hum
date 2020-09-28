import React, {  useState, useEffect } from 'react'
import API from '../../services/API';
import { Button } from 'react-bootstrap';
import CanchaList from '../../components/Cancha/CanchaList';
import CanchaModal from '../../components/Cancha/CanchaModal';

const Cancha = props => {

  const [canchas,setCanchas] = useState([]);

  const [showModal,setShowModal] = useState(false);
  const [isEdit,setIsEdit] = useState(false);
  const [validateForm, setValidateForm] = useState(false);
  const [errorMsg,setErrorMsg] = useState('');

  const initialCanchaData = { 
    nombre: '',
    direccion: ''
  };

  const [newCanchaData,setNewCanchaData] = useState(initialCanchaData); 

  useEffect(() => {
    getCanchas();
  }, []);

  const getCanchas = async() => {
    try{
      let canchas = await API.get('/canchas');

      canchas = await calcularPartidosEnElDia(canchas);
      setCanchas(canchas);
    }
    catch(error){
      console.log(error);
    }
  }

  const calcularPartidosEnElDia = async(canchas) => {
    let partidos = await API.get('/partidos');

    return canchas = canchas.map((cancha) => {
      // Inicializa un objeto date al día de hoy sin la hora
      let hoy = new Date(Date.now()).setHours(0, 0, 0, 0);

      // Filtra el listado de partidos por los que coincidan con la fecha de hoy y el nombre de la cancha
      let partidosHoy = partidos.filter(
        partido => new Date(partido.fechaComienzo).setHours(0, 0, 0, 0) === hoy 
                    && partido.cancha.nombre === cancha.nombre);

      // Setea la cantidad del listado filtrado
      cancha.partidosEnElDia = partidosHoy.length;

      return cancha;
    });
  }

  const borrarCancha = async(id) => {
    if (window.confirm("Estas seguro?")) {
      try{
        await API.remove(`/canchas/${id}`);
        getCanchas();
      }
      catch(error){
        console.log(error);
      }
    }
  }

  const agregarCancha = async() => {
    try{
      await API.save('/canchas', newCanchaData);
      resetModal();
      getCanchas();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const editarCancha = async(id) => {
    try{
      await API.update(`/canchas/${id}`, newCanchaData);
      resetModal();
      getCanchas();
    }
    catch(error){
      setErrorMsg(JSON.stringify(error));
    }
  }

  const handleFormChange = (tipo,value) => {
    if(value === '')
      setValidateForm(true);

    setNewCanchaData({...newCanchaData, [tipo]:value});
  }  

  const handleFormSubmit = (form, isEdit) => {
    setValidateForm(true);

    if(form.checkValidity())
      isEdit ? editarCancha(newCanchaData.id) : agregarCancha();
  };
 
  const resetModal = () =>{
    setShowModal(false);
    setIsEdit(false);
    setNewCanchaData(initialCanchaData);
    setValidateForm(false);
    setErrorMsg('');
  }

  const handleOpenModal = (editar = false, canchaToEdit = null) =>{
    if(editar)
    {
      setIsEdit(true);
      setNewCanchaData(canchaToEdit);
    }
    setShowModal(true);
  }

  const handleCloseModal = () =>{
    resetModal();
  }

  return (
    <div className="container mt-4 mr-auto">
      <h1>Canchas</h1>
      <Button variant="info mb-3" onClick={()=> handleOpenModal()}>Agregar Cancha</Button> 
      <CanchaModal
        show={showModal}
        handleClose={handleCloseModal}
        handleChange={handleFormChange}
        handleSubmit={handleFormSubmit}
        isEdit={isEdit}
        validate={validateForm}
        errorMsg={errorMsg}
        cancha={newCanchaData}
      />
      <CanchaList 
        canchas={canchas}
        borrarCancha={borrarCancha}
        editarCancha={handleOpenModal}
      />
    </div>
  );
  
}

export default Cancha;

