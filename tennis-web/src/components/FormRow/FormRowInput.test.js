import React from "react";
import FormRowInput from "./FormRowInput";
import {shallow} from 'enzyme';

describe('FormRowInput', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<FormRowInput/>);
    expect(wrapper.exists()).toBe(true);
  });

});