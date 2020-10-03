import React, { useRef } from 'react';
import { Form, Button, Modal } from 'react-bootstrap';
import FormRowInput from '../FormRow/FormRowInput';
import FormRowSelect from '../FormRow/FormRowSelect';


const TorneoModal = (props) => {

  const formRef = useRef(null);
  const {show, handleClose, handleChange, handleSubmit, 
         isEdit, validate, errorMsg, torneo, listaJugadores, listaCanchas} = props;

  const jugadores = listaJugadores.map(jugador => {
    return <option key={jugador.id} value={jugador.id}>{jugador.nombre}</option>
  })
  const canchas = listaCanchas.map(cancha => {
    return <option key={cancha.id} value={cancha.id}>{cancha.nombre}</option>
  })

  return (
    <Modal
      show={show}
      onHide={handleClose}
      backdrop="static" //Si se hace click fuera del modal este no se cerrara
      keyboard={false}  //Si se presiona la tecla ESC tampoco se cierra
    >
      <Modal.Header closeButton>
        <Modal.Title>{isEdit ? 'Editar Partido' : 'Agregar Torneo'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="Form" noValidate validated={validate} ref={formRef}>

          <FormRowInput
            label={"Fecha / Hora de inicio"}
            type={"text"}
            placeholder={"DD/MM/YYYY hh:mm"}
            value={torneo.fechaComienzo}
            handleChange={handleChange}
            property={"fechaComienzo"}
          />
          <FormRowSelect
            label={"Jugador 1"}
            placeholder={"Elige un jugador"}
            value={torneo.jugador1.id}
            handleChange={handleChange}
            property={"jugador1"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Jugador 2"}
            placeholder={"Elige un jugador"}
            value={torneo.jugador2.id}
            handleChange={handleChange}
            property={"jugador2"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Jugador 3"}
            placeholder={"Elige un jugador"}
            value={torneo.jugador3.id}
            handleChange={handleChange}
            property={"jugador3"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Jugador 4"}
            placeholder={"Elige un jugador"}
            value={torneo.jugador4.id}
            handleChange={handleChange}
            property={"jugador4"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Cancha"}
            placeholder={"Elige una cancha"}
            value={torneo.cancha.id}
            handleChange={handleChange}
            property={"cancha"}
            options={canchas}
          />

          {errorMsg !== '' &&
            <Form.Group className="alert-danger">
              {errorMsg}
            </Form.Group>
          }

        </Form>
      </Modal.Body>
      <Modal.Footer>    
        <Button variant="success" onClick={() => handleSubmit(formRef.current, isEdit)}> {isEdit ? 'Editar' : 'Agregar'}</Button>
        <Button variant="danger" className="mr-2" onClick={()=>handleClose()}> Cancelar </Button>
      </Modal.Footer>
    </Modal>
  );


}

export default TorneoModal;