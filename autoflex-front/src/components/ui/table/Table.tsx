import "./Table.css";

interface Column<T> {
  header: string;
  accessor: string;
  cell?: (value: any, row: T) => React.ReactNode;
}

interface TableProps<T> {
  columns: Column<T>[];
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
                <td key={col.accessor}>
                  {col.cell
                    ? col.cell(row[col.accessor], row)
                    : row[col.accessor]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    );
  }

export default Table;