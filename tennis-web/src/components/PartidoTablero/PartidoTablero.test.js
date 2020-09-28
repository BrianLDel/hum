import React from "react";
import PartidoTablero from "./PartidoTablero";
import {shallow} from 'enzyme';

describe('PartidoTablero', ()=>{
  
  let wrapper;

  beforeEach(() => {
    //Espera recibir los datos del partido por location.state.partido por eso se usa el routeComponentPropsMock
    wrapper = shallow(<PartidoTablero  {...routeComponentPropsMock}/>);
  });

  it("should render", () => {
    expect(wrapper.exists()).toBe(true);
  });

  const routeComponentPropsMock = {
    history: {},
    location: { state: { partido: 
      {
        scoreLocal: 0,
        puntosGameActualLocal: 0,
        cantidadGamesLocal: 4,
        scoreVisitante: 0,
        puntosGameActualVisitante: 0,
        cantidadGamesVisitante: 6,
        id: 1,
        fechaComienzo: "2020-04-05T03:00:00.000Z",
        estado: "FINALIZADO",
        jugadorLocal: {
            nombre: "Juan",
            puntos: 10,
            id: 3
        },
        jugadorVisitante: {
            nombre: "Pablo",
            puntos: 20,
            id: 1
        },
        cancha: {
            nombre: "Cancha",
            direccion: "476",
            id: 2
        }
      }
    }},
    match: {},
  }

});