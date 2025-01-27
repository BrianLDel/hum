import React from 'react'
import { Route, Switch } from 'react-router-dom';
import Jugador from '../containers/Jugador/Jugador';
import Cancha from '../containers/Cancha/Cancha';
import Partido from '../containers/Partido/Partido';
import PartidoTablero from '../components/PartidoTablero/PartidoTablero'
import Entrenador from '../containers/Entrenador/Entrenador'
import Torneo from '../containers/Torneo/Torneo';

export default function Routes() {
  
  return (
    <Switch>
      <Route path="/jugadores" component={Jugador} exact />
      <Route path="/canchas" component={Cancha} exact />
      <Route path="/partidos" component={Partido} exact/>
      <Route path="/partidos/jugar-partido" component={PartidoTablero} exact/>
      <Route path="/entrenadores" component={Entrenador} exact/>
      <Route path="/torneos" component={Torneo} exact/>
    </Switch>
  )
}
