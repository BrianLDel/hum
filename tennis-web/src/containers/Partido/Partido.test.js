import React from "react";
import Partido from "./Partido";
import {shallow} from 'enzyme';

describe('Partido', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<Partido/>);
    expect(wrapper.exists()).toBe(true);
  });

});