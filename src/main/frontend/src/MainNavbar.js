import {Button, Container, Nav, Navbar} from "react-bootstrap";
import Offcanvas from 'react-bootstrap/Offcanvas';
import {useState} from "react";
import guideText from "./constants";

const MainNavbar = () => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => {
    setShow(true);
  }

  return (
    <div>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="/">Hauzijan</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="/">Search</Nav.Link>
              <Button className="custom-button" onClick={handleShow} variant="dark">Guide</Button>
              <Nav.Link href="/contact">Contact</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Offcanvas show={show} onHide={handleClose}>
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Guide</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          {guideText()}
        </Offcanvas.Body>
      </Offcanvas>
    </div>
  );
}

export default MainNavbar;