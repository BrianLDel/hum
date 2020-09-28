import React, { useRef } from 'react';
import { Form, Button, Modal } from 'react-bootstrap'
import FormRowInput from '../FormRow/FormRowInput'

const JugadorModal = (props) => {

  const formRef = useRef(null);
  const {show, handleClose, handleChange, handleSubmit, isEdit, validate, errorMsg,jugador} = props;

  return (
    <Modal
      show={show}
      onHide={handleClose}
      backdrop="static" //Si se hace click fuera del modal este no se cerrara
      keyboard={false}  //Si se presiona la tecla ESC tampoco se cierra
    >
      <Modal.Header closeButton>
        <Modal.Title>{isEdit ? 'Editar Jugador' : 'Agregar Jugador'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="Form" noValidate validated={validate} ref={formRef}>

          <FormRowInput
            label={"Nombre"}
            type={"text"}
            placeholder={"Ingrese un nombre"}
            value={jugador.nombre}
            handleChange={handleChange}
            property={"nombre"}
          />
          <FormRowInput
            label={"Puntos"}
            type={"number"}
            placeholder={"Ingrese un numero"}
            value={jugador.puntos}
            handleChange={handleChange}
            property={"puntos"}
          />

          {errorMsg !== '' &&
            <Form.Group className="alert-danger">
              {errorMsg}
            </Form.Group>
          }

        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="success" onClick={() => handleSubmit(formRef.current, isEdit)}>{isEdit ? 'Editar' : 'Agregar'}</Button>
        <Button variant="danger" className="mr-2" onClick={()=>handleClose()}>Cancelar</Button>
      </Modal.Footer>
    </Modal>
  );
  
}

export default JugadorModal;




