import { IconContext } from "react-icons";
import "./NoIndicator.css";
import { MdCancel } from "react-icons/md";

export function NoIndicator() {
  return (
    <div className="no-indicator-container">
      <IconContext.Provider
        value={{size: '20px' }}
      >
      <MdCancel className="no-indicator-icon"/>
      </IconContext.Provider>
      <p className="no-indicator-text"> No </p>
    </div>
  );
}

export default NoIndicator;