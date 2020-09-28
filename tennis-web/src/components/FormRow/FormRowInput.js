import React from 'react'
import { Form, Col } from 'react-bootstrap';

const FormRowInput = props => {

  const {property, label, type,value, handleChange, placeholder} = props;

  return (
    <Form.Group controlId={property}>
      <Form.Row>
        <Col className="col-md-3">
          <Form.Label >{label}</Form.Label>
        </Col>
        <Col className="col-md-9">
          <Form.Control noValidate
            required
            type={type}
            placeholder={placeholder}
            value={value}
            onChange={(e) => handleChange(property, e.target.value)}
          />
          <Form.Control.Feedback type="invalid">
            {label} es requerido.
          </Form.Control.Feedback>
        </Col>
      </Form.Row>
    </Form.Group>
  );
  
}

export default FormRowInput;
