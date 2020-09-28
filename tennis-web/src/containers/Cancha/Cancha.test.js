import React from "react";
import Cancha from "./Cancha";
import {shallow} from 'enzyme';

describe('Cancha', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<Cancha/>);
    expect(wrapper.exists()).toBe(true);
  });

});