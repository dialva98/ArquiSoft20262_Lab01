import { useState } from "react";

function TransferForm({ customers, onTransferComplete }) {
    const [senderAccountNumber, setSenderAccountNumber] = useState("");
    const [receiverAccountNumber, setReceiverAccountNumber] = useState("");
    const [amount, setAmount] = useState("");

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();

        setMessage("");
        setError("");

        if (!senderAccountNumber || !receiverAccountNumber) {
            setError("Debes seleccionar la cuenta de origen y destino.");
            return;
        }

        if (senderAccountNumber === receiverAccountNumber) {
            setError("La cuenta de origen y destino no pueden ser la misma.");
            return;
        }

        if (!amount || Number(amount) <= 0) {
            setError("El monto debe ser mayor que cero.");
            return;
        }

        setLoading(true);

        try {
            const response = await fetch(
                "http://localhost:8080/api/transactions",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        senderAccountNumber,
                        receiverAccountNumber,
                        amount: Number(amount),
                    }),
                }
            );

            /*
             * El backend puede devolver:
             *
             * 200 -> JSON con la transacción
             *
             * 400 -> texto plano como:
             *        "Saldo insuficiente"
             *
             * Por eso primero obtenemos el texto
             * y solamente intentamos convertirlo a JSON
             * cuando realmente sea necesario.
             */

            const responseText = await response.text();

            if (!response.ok) {
                setError(
                    responseText || "No fue posible realizar la transferencia."
                );
                return;
            }

            let transaction;

            try {
                transaction = JSON.parse(responseText);
            } catch {
                transaction = null;
            }

            setMessage(
                transaction
                    ? "Transferencia realizada correctamente."
                    : "Transferencia realizada correctamente."
            );

            setAmount("");
            setSenderAccountNumber("");
            setReceiverAccountNumber("");

            /*
             * Actualiza los clientes en App.jsx
             * para mostrar los nuevos saldos.
             */
            if (onTransferComplete) {
                onTransferComplete();
            }

        } catch (error) {
            console.error("Error realizando transferencia:", error);

            setError(
                "No fue posible conectar con el servidor."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="transfer-card">

            <div className="transfer-header">
                <div>
                    <h2>Realizar transferencia</h2>

                    <p>
                        Envía dinero entre las cuentas del banco.
                    </p>
                </div>
            </div>

            <form onSubmit={handleSubmit}>

                <div className="form-group">

                    <label htmlFor="sender">
                        Cuenta de origen
                    </label>

                    <select
                        id="sender"
                        value={senderAccountNumber}
                        onChange={(event) =>
                            setSenderAccountNumber(event.target.value)
                        }
                    >
                        <option value="">
                            Selecciona una cuenta
                        </option>

                        {customers.map((customer) => (
                            <option
                                key={customer.id}
                                value={customer.accountNumber}
                            >
                                {customer.firstName} {customer.lastName} -{" "}
                                {customer.accountNumber}
                            </option>
                        ))}
                    </select>

                </div>

                <div className="form-group">

                    <label htmlFor="receiver">
                        Cuenta de destino
                    </label>

                    <select
                        id="receiver"
                        value={receiverAccountNumber}
                        onChange={(event) =>
                            setReceiverAccountNumber(event.target.value)
                        }
                    >
                        <option value="">
                            Selecciona una cuenta
                        </option>

                        {customers.map((customer) => (
                            <option
                                key={customer.id}
                                value={customer.accountNumber}
                            >
                                {customer.firstName} {customer.lastName} -{" "}
                                {customer.accountNumber}
                            </option>
                        ))}
                    </select>

                </div>

                <div className="form-group">

                    <label htmlFor="amount">
                        Monto a transferir
                    </label>

                    <input
                        id="amount"
                        type="number"
                        min="0.01"
                        step="0.01"
                        placeholder="Ej: 250"
                        value={amount}
                        onChange={(event) =>
                            setAmount(event.target.value)
                        }
                    />

                </div>

                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

                {message && (
                    <div className="success-message">
                        {message}
                    </div>
                )}

                <button
                    type="submit"
                    className="primary-button transfer-submit"
                    disabled={loading}
                >
                    {loading
                        ? "Procesando..."
                        : "Realizar transferencia"}
                </button>

            </form>
        </div>
    );
}

export default TransferForm;
