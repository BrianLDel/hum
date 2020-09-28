import React from "react";
import CanchaModal from "./CanchaModal";
import {shallow} from 'enzyme';

describe('CanchaModal', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<CanchaModal {...canchaModalProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});

const canchaModalProps = {
  show:null, 
  handleClose:null,
  handleChange:null,
  handleSubmit:null, 
  isEdit:null, 
  validate:null, 
  errorMsg:null,
  cancha:{
    nombre: '',
    direccion: ''
  }
}