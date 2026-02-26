import "./Table.css";

interface Column {
  header: string;
  accessor: string;
}

interface TableProps<T> {
  columns: Column[];
  data: T[];
}

export function Table<T extends Record<string, any>>({
  columns,
  data,
}: TableProps<T>) {
  return (
    <div className="table-card">
      <table className="custom-table">
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.accessor}>{col.header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => (
            <tr key={index}>
              {columns.map((col) => (
                <td key={col.accessor}>{row[col.accessor]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    );
  }

export default Table;