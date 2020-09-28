import React, { useState } from 'react';
import API from '../../services/API';
import { Table, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom'

const PartidoTablero = props => {

  const [partido,setPartido] = useState(props.location.state.partido);

  const sumarPunto = async(id, modoJugador) => {
    try{
      let response = await API.save(`/partidos/${id}/actions/sumar-punto`, null, {modoJugador});
      setPartido(response);
    }
    catch(error){
      console.log(error);
    }
  }

  return (
    <div className="container mt-4">
      <h1>Tablero</h1>
      <Table>
        <thead>
          <tr>
            <th>Jugador</th>
            <th>Puntos</th>
            <th>Games</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{partido.jugadorLocal.nombre}</td>
            <td>{partido.puntosGameActualLocal}</td>
            <td>{partido.cantidadGamesLocal}</td>
            <td>
              {partido.estado!=='FINALIZADO' &&
                <Button variant="primary" size="sm" onClick={()=>sumarPunto(partido.id, 'LOCAL')}>Sumar punto</Button>
              }
            </td>
          </tr>
          <tr>
            <td>{partido.jugadorVisitante.nombre}</td>
            <td>{partido.puntosGameActualVisitante}</td>
            <td>{partido.cantidadGamesVisitante}</td>
            <td>
              {partido.estado!=='FINALIZADO' &&
                <Button variant="primary" size="sm" onClick={()=>sumarPunto(partido.id, 'VISITANTE')}>Sumar punto</Button>    
              }
            </td>
          </tr>
        </tbody>
      </Table>
      <Link to='/partidos'>
       <Button variant="primary" className="mr-2">Volver</Button>
      </Link>
    </div>
  );
}

export default PartidoTablero;