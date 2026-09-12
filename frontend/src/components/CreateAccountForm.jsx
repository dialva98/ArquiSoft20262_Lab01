/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

import { useState } from "react";

function CreateAccountForm({ onCustomerCreated }) {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [accountNumber, setAccountNumber] = useState("");
    const [balance, setBalance] = useState("");

    const [error, setError] = useState("");
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();

        setError("");
        setMessage("");

        if (!firstName.trim()) {
            setError("El nombre es obligatorio.");
            return;
        }

        if (!lastName.trim()) {
            setError("El apellido es obligatorio.");
            return;
        }

        if (!accountNumber.trim()) {
            setError("El número de cuenta es obligatorio.");
            return;
        }

        if (!balance || Number(balance) < 0) {
            setError("El saldo no puede ser negativo.");
            return;
        }

        setLoading(true);

        try {
            const response = await fetch(
                "http://localhost:8080/api/customers",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        firstName: firstName.trim(),
                        lastName: lastName.trim(),
                        accountNumber: accountNumber.trim(),
                        balance: Number(balance),
                    }),
                }
            );

            const responseText = await response.text();

            if (!response.ok) {
                setError(
                    responseText ||
                    "No fue posible crear el cliente."
                );
                return;
            }

            setMessage("Cliente creado correctamente.");

            setFirstName("");
            setLastName("");
            setAccountNumber("");
            setBalance("");

            if (onCustomerCreated) {
                onCustomerCreated();
            }

        } catch (error) {
            console.error("Error creando cliente:", error);

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
                    <h2>Crear nuevo cliente</h2>

                    <p>
                        Registra una nueva cuenta bancaria.
                    </p>
                </div>
            </div>

            <form onSubmit={handleSubmit}>

                <div className="form-group">
                    <label htmlFor="firstName">
                        Nombre
                    </label>

                    <input
                        id="firstName"
                        type="text"
                        placeholder="Ej: Carlos"
                        value={firstName}
                        onChange={(event) =>
                            setFirstName(event.target.value)
                        }
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="lastName">
                        Apellido
                    </label>

                    <input
                        id="lastName"
                        type="text"
                        placeholder="Ej: Gómez"
                        value={lastName}
                        onChange={(event) =>
                            setLastName(event.target.value)
                        }
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="accountNumber">
                        Número de cuenta
                    </label>

                    <input
                        id="accountNumber"
                        type="text"
                        placeholder="Ej: 123456799"
                        value={accountNumber}
                        onChange={(event) =>
                            setAccountNumber(event.target.value)
                        }
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="balance">
                        Saldo inicial
                    </label>

                    <input
                        id="balance"
                        type="number"
                        min="0"
                        step="0.01"
                        placeholder="Ej: 1000"
                        value={balance}
                        onChange={(event) =>
                            setBalance(event.target.value)
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
                        ? "Creando cliente..."
                        : "Crear cliente"}
                </button>

            </form>
        </div>
    );
}

export default CreateAccountForm;
