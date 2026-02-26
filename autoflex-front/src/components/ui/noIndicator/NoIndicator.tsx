import { IconContext } from "react-icons";
import "./NoIndicator.css";
import { FcCancel } from "react-icons/fc";

export function NoIndicator() {
  return (
    <div className="no-indicator-container">
        <IconContext.Provider
            value={{size: '20px' }}
        >
        <div>
            <FcCancel className="no-indicator-icon"/>
        </div>
        </IconContext.Provider>
        <p className="no-indicator-text"> No </p>
    </div>
  );
}

export default NoIndicator;