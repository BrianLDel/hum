import React from "react";
import PartidoModal from "./PartidoModal";
import {shallow} from 'enzyme';

describe('PartidoModal', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<PartidoModal {...partidoModalProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});

const partidoModalProps = {
  show:null, 
  handleClose:null,
  handleChange:null,
  handleSubmit:null, 
  isEdit:null, 
  validate:null, 
  errorMsg:null,
  listaJugadores:[], 
  listaCanchas:[],
  partido:{
    fechaComienzo: '',
    jugadorLocal: {
      id: -1
    },
    jugadorVisitante: {
      id: -1
    },
    cancha: {
      id: -1
    },
    estado: 'NO_INICIADO'
  }
} 