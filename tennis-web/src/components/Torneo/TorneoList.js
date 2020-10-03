import React from 'react';
import { Table, Button } from 'react-bootstrap';


const TorneoList = (props) => {

  const {torneos, borrarTorneo, editarTorneo,generarPartidos} = props;

  const listaTorneos = torneos.map((torneo) => {

    const {id, jugador1, jugador2, jugador3, jugador4, fechaComienzo, cancha} = torneo;

    return (
      <tr key={id}>
        <td>{id}</td>
        <td>{jugador1.nombre}</td>
        <td>{jugador2.nombre}</td>
        <td>{jugador3.nombre}</td>
        <td>{jugador4.nombre}</td>
        <td>{fechaComienzo}</td>
        <td>{cancha.nombre}</td>
        <td>
          <Button variant="primary" className="mr-2" onClick={()=>generarPartidos(id)}> Generar </Button>
          <Button variant="success" className="mr-2" onClick={()=>editarTorneo(true, torneo)}> Editar </Button>
          <Button variant="danger" onClick={()=>borrarTorneo(id)}>Eliminar</Button>
        </td>
      </tr>
    )
  });

  return (
    <Table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Jugador 1</th>
          <th>Jugador 2</th>
          <th>Jugador 3</th>
          <th>Jugador 4</th>
          <th>Fecha de Comienzo</th>
          <th>Cancha</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {listaTorneos}
      </tbody>
    </Table>
  );

}

export default TorneoList;