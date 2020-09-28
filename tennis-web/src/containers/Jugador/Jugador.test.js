import React from "react";
import Jugador from "./Jugador";
import {shallow} from 'enzyme';

describe('Jugador', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<Jugador/>);
    expect(wrapper.exists()).toBe(true);
  });

});