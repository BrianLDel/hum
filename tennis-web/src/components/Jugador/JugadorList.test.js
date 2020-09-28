import React from "react";
import JugadorList from "./JugadorList";
import {shallow} from 'enzyme';

describe('JugadorList', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<JugadorList {...jugadorListProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});

const jugadorListProps = {
  jugadores:[], 
  borrarJugador:null, 
  editarJugador:null, 
  recalcularRanking:null
}