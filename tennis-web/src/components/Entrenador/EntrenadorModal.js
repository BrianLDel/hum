import React, { useRef } from 'react'
import { Button, Form, Modal } from 'react-bootstrap';
import FormRowInput from '../FormRow/FormRowInput'

const EntrenadorModal = props => {

  const formRef = useRef(null);
  const {show, handleClose, handleChange, handleSubmit, isEdit, validate, errorMsg, entrenador} = props;

  return (
    <Modal
      show={show}
      onHide={handleClose}
      backdrop="static" 
      keyboard={false}  
    >
      <Modal.Header closeButton>
        <Modal.Title>{isEdit ? 'Editar Entrenador' : 'Agregar Entrenador'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="Form" noValidate validated={validate} ref={formRef}>

          <FormRowInput
            label={"Nombre"}
            type={"text"}
            placeholder={"Ingrese un nombre"}
            value={entrenador.nombre}
            handleChange={handleChange}
            property={"nombre"}
          />

          {errorMsg !== '' &&
            <Form.Group className="alert-danger">
              {errorMsg}
            </Form.Group>
          }

        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="success" onClick={() => handleSubmit(formRef.current, isEdit)}> {isEdit ? 'Editar' : 'Agregar'} </Button>
        <Button variant="danger" className="ml-2" onClick={()=>handleClose()}> Cancelar </Button>
      </Modal.Footer>
    </Modal>
  );
  
}

export default EntrenadorModal;
