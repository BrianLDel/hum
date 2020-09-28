import React from "react";
import PartidoList from "./PartidoList";
import {shallow} from 'enzyme';

describe('PartidoList', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<PartidoList {...partidoListProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});

const partidoListProps = {
  partidos:[], 
  borrarPartido:null, 
  editarPartido:null, 
  iniciarPartido:null
} 