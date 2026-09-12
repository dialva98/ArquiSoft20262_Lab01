function CustomerList({ customers }) {
  if (!customers || customers.length === 0) {
    return (
        <div className="empty-customers">
          <p>No hay clientes registrados.</p>
        </div>
    );
  }

  return (
      <div className="customer-list">
        {customers.map((customer) => {
          const initials =
              `${customer.firstName?.charAt(0) || ""}${customer.lastName?.charAt(0) || ""}`.toUpperCase();

          return (
              <div className="customer-card" key={customer.id}>
                <div className="customer-info">
                  <div className="customer-avatar">
                    {initials}
                  </div>

                  <div className="customer-details">
                    <h3 className="customer-name">
                      {customer.firstName} {customer.lastName}
                    </h3>

                    <p className="customer-account">
                      Cuenta · {customer.accountNumber}
                    </p>
                  </div>
                </div>

                <div className="customer-balance">
                            <span className="balance-label">
                                SALDO DISPONIBLE
                            </span>

                  <span className="balance-value">
                                ${Number(customer.balance).toLocaleString(
                      "es-CO",
                      {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2,
                      }
                  )}
                            </span>
                </div>
              </div>
          );
        })}
      </div>
  );
}

export default CustomerList;