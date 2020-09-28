import React from 'react'
import { Route, Switch } from 'react-router-dom';
import Jugador from '../containers/Jugador/Jugador';
import Cancha from '../containers/Cancha/Cancha';
import Partido from '../containers/Partido/Partido';
import PartidoTablero from '../components/PartidoTablero/PartidoTablero'

export default function Routes() {
  
  return (
    <Switch>
      <Route path="/jugadores" component={Jugador} exact />
      <Route path="/canchas" component={Cancha} exact />
      <Route path="/partidos" component={Partido} exact/>
      <Route path="/partidos/jugar-partido" component={PartidoTablero} exact/>
    </Switch>
  )
}
