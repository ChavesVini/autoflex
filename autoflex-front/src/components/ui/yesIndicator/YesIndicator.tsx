import { IconContext } from "react-icons";
import "./YesIndicator.css";
import { FcOk } from "react-icons/fc";

export function YesIndicator() {
  return (
    <div className="yes-indicator-container">
      <IconContext.Provider
        value={{size: '20px' }}
      >
      <FcOk className="yes-indicator-icon"/>
      </IconContext.Provider>
      <p className="yes-indicator-text"> Yes </p>
    </div>
  );
}

export default YesIndicator;