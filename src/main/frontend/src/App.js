import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Home from "./page/Home";
import Contact from "./page/Contact";
import MainNavbar from "./MainNavbar";

function App() {
  return (
    <Router>
      <div className="App">
        <MainNavbar/>
        <Switch>
          <Route exact path="/">
            <Home/>
          </Route>
          <Route path="/contact">
            <Contact/>
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
