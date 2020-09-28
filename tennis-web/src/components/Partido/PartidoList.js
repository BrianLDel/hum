import React from 'react'
import { Table, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom'

const PartidoList = props => {

  const {partidos, borrarPartido, editarPartido, iniciarPartido} = props;

  const listaPartidos = partidos.map((partido) => {

    const {id,fechaComienzo,jugadorLocal,jugadorVisitante,cancha,estado} = partido;
    return (
      <tr key={id}>
        <td>{id}</td>
        <td>{fechaComienzo}</td>
        <td>{jugadorLocal.nombre}</td>
        <td>{jugadorVisitante.nombre}</td>
        <td>{cancha.nombre}</td>
        <td>{estado}</td>
        <td className="text-right">
          {estado ==='NO_INICIADO'
            ? <>
                <Link to={{
                  pathname: '/partidos/jugar-partido',
                  state: { partido: partido }
                  }}>                                                     
                    <Button variant="primary" className="mr-2" onClick={()=> iniciarPartido(id)}> Jugar</Button>
                </Link>
                <Button variant="success" className="mr-2" onClick={()=>editarPartido(true,partido)}> Editar </Button>
                <Button variant="danger" onClick={()=>borrarPartido(id)}>Eliminar</Button>
              </>
            : <Link to={{
                pathname: '/partidos/jugar-partido',
                state: { partido: partido }
                }}>                                                     
                  <Button variant="primary" className="mr-2"> {estado ==='FINALIZADO' ? 'Ver':'Jugar'}</Button>
              </Link>
          }
        </td>
      </tr>
    )
  });

  return (
    <Table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Fecha Comienzo</th>
          <th>Local</th>
          <th>Visitante</th>
          <th>Cancha</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {listaPartidos}
      </tbody>
    </Table>
  );
  
}

export default PartidoList;