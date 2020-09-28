import React from "react";
import Menu from "./Menu";
import {shallow} from 'enzyme';

describe('Menu', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<Menu/>);
    expect(wrapper.exists()).toBe(true);
  });

});