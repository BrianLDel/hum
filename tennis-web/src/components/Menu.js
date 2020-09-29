import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Menu()  {
    //Este menu utiliza react-bootstrap
    
    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <div className="container">
                <Navbar.Brand as={Link} to="/">Baufest Tennis</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/jugadores">Jugadores</Nav.Link>
                        <Nav.Link as={Link} to="/partidos">Partidos</Nav.Link>
                        <Nav.Link as={Link} to="/canchas">Canchas</Nav.Link>
                        <Nav.Link as={Link} to="/entrenadores">Entrenadores</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </div>
        </Navbar>
    );  
}

export default Menu;
