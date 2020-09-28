import React from "react";
import CanchaList from "./CanchaList";
import {shallow} from 'enzyme';

describe('CanchaList', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<CanchaList {...canchaListProps}/>);
    expect(wrapper.exists()).toBe(true);
  });

});

const canchaListProps = { 
  canchas:[],
  borrarCancha:null,
  editarCancha:null 
};