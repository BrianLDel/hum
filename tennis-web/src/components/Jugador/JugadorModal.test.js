import React from "react";
import JugadorModal from "./JugadorModal";
import {shallow} from 'enzyme';

describe('JugadorModal', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<JugadorModal {...jugadorModalProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});


const jugadorModalProps = {
  show:null, 
  handleClose:null,
  handleChange:null,
  handleSubmit:null, 
  isEdit:null, 
  validate:null, 
  errorMsg:null,
  jugador:{
    nombre: '',
    puntos: 0
  }
} 