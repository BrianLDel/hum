import React from "react";
import FormRowSelect from "./FormRowSelect";
import {shallow} from 'enzyme';

describe('FormRowSelect', ()=>{
  
  let wrapper;

  it("should render", () => {
    wrapper = shallow(<FormRowSelect/>);
    expect(wrapper.exists()).toBe(true);
  });

});