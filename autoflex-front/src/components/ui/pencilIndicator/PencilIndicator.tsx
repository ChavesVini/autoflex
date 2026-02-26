import { RiPencilFill } from "react-icons/ri";
import { IconContext } from "react-icons";

export function PencilIndicator() {
  return (
    <div className="pencil-indicator-container">
      <></>
      <IconContext.Provider
          value={{ color: 'blue', size: '20px' }}
        >
        <div>
          <RiPencilFill />
        </div>
      </IconContext.Provider>
    </div>
  );
}

export default PencilIndicator;