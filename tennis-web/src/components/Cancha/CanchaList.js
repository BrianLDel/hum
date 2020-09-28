import React from 'react'
import { Table, Button } from 'react-bootstrap';

const CanchaList = props => {

  const { canchas, borrarCancha, editarCancha } = props;

  const listaCanchas = canchas.map((cancha) => {

    const {id, nombre, direccion, partidosEnElDia} = cancha;
    return (
      <tr key={id}>
        <td>{id}</td>
        <td>{nombre}</td>
        <td>{direccion}</td>
        <td>{partidosEnElDia}</td>
        <td>
          <Button variant="success" className="mr-2" onClick={() => editarCancha(true, cancha)}> Editar </Button>
          <Button variant="danger" onClick={()=>borrarCancha(id)}> Eliminar </Button>
        </td>
      </tr>
    )
  });

  return (
    <Table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Direccion</th>
          <th>Partidos hoy</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {listaCanchas}
      </tbody>
    </Table>
  );
  
}

export default CanchaList;

