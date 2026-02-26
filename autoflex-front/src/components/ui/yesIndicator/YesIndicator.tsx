import { IconContext } from "react-icons";
import "./YesIndicator.css";
import { FcOk } from "react-icons/fc";

export function YesIndicator() {
  return (
    <div className="yes-indicator-container">
      <IconContext.Provider
        value={{size: '20px' }}
      >
      <div>
        <FcOk className="yes-indicator-icon"/>
      </div>
      </IconContext.Provider>
      <p className="yes-indicator-text"> Yes </p>
    </div>
  );
}

export default YesIndicator;