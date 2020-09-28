import React from 'react'
import { Table, Button } from 'react-bootstrap';

const JugadorList = props => {

  const {jugadores, borrarJugador, editarJugador, recalcularRanking} = props;

  const orderByName = (jugadores) => {
    return jugadores.sort((a, b) => a.nombre.localeCompare(b.nombre))
  }

  const listaJugadores = orderByName(jugadores).map((jugador) => {

    const {id, nombre, puntos} = jugador;
    return (
      <tr key={id}>
        <td>{id}</td>
        <td>{nombre}</td>
        <td>{puntos}</td>
        <td>
          <Button variant="primary" className="mr-2" onClick={()=>recalcularRanking(id)}> Recalcular ranking </Button>
          <Button variant="success" className="mr-2" onClick={()=>editarJugador(true,jugador)}> Editar </Button>
          <Button variant="danger" onClick={()=>borrarJugador(id)}>Eliminar</Button>
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
          <th>Puntos</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {listaJugadores}
      </tbody>
    </Table>
  );

}

export default JugadorList;