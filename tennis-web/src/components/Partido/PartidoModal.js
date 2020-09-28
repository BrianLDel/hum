import React, { useRef } from 'react';
import { Form, Button, Modal } from 'react-bootstrap'
import FormRowInput from '../FormRow/FormRowInput'
import FormRowSelect from '../FormRow/FormRowSelect'

const PartidoModal = props => {

  const formRef = useRef(null);
  const {show, handleClose, handleChange, handleSubmit, 
         isEdit, validate, errorMsg, partido, listaJugadores, listaCanchas} = props;

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
        <Modal.Title>{isEdit ? 'Editar Partido' : 'Agregar Partido'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="Form" noValidate validated={validate} ref={formRef}>

          <FormRowInput
            label={"Fecha / Hora de inicio"}
            type={"text"}
            placeholder={"DD/MM/YYYY hh:mm"}
            value={partido.fechaComienzo}
            handleChange={handleChange}
            property={"fechaComienzo"}
          />
          <FormRowSelect
            label={"Jugador Local"}
            placeholder={"Elige un jugador"}
            value={partido.jugadorLocal.id}
            handleChange={handleChange}
            property={"jugadorLocal"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Jugador Visitante"}
            placeholder={"Elige un jugador"}
            value={partido.jugadorVisitante.id}
            handleChange={handleChange}
            property={"jugadorVisitante"}
            options={jugadores}
          />
          <FormRowSelect
            label={"Cancha"}
            placeholder={"Elige una cancha"}
            value={partido.cancha.id}
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

export default PartidoModal;


