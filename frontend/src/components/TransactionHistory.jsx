/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

import { useEffect, useState } from "react";

function TransactionHistory({ customers }) {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadTransactions = async () => {
            if (!customers || customers.length === 0) {
                setTransactions([]);
                setLoading(false);
                return;
            }

            try {
                setLoading(true);
                setError("");

                const responses = await Promise.all(
                    customers.map((customer) =>
                        fetch(
                            `http://localhost:8080/api/transactions/${customer.accountNumber}`
                        )
                    )
                );

                for (const response of responses) {
                    if (!response.ok) {
                        throw new Error(
                            "No fue posible obtener el historial"
                        );
                    }
                }

                const results = await Promise.all(
                    responses.map((response) => response.json())
                );

                const allTransactions = results.flat();

                // El mismo movimiento puede aparecer
                // asociado a las dos cuentas.
                const uniqueTransactions = Array.from(
                    new Map(
                        allTransactions.map((transaction) => [
                            transaction.id,
                            transaction,
                        ])
                    ).values()
                );

                uniqueTransactions.sort((a, b) => {
                    return (b.id || 0) - (a.id || 0);
                });

                setTransactions(uniqueTransactions);
            } catch (err) {
                console.error(err);
                setError("No fue posible cargar el historial.");
            } finally {
                setLoading(false);
            }
        };

        loadTransactions();
    }, [customers]);

    const getCustomerName = (accountNumber) => {
        const customer = customers.find(
            (customer) => customer.accountNumber === accountNumber
        );

        if (!customer) {
            return accountNumber;
        }

        return `${customer.firstName} ${customer.lastName}`;
    };

    if (loading) {
        return (
            <div className="transaction-history">
                <p className="history-message">
                    Cargando historial...
                </p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="transaction-history">
                <p className="history-error">{error}</p>
            </div>
        );
    }

    if (transactions.length === 0) {
        return (
            <div className="transaction-history">
                <p className="history-message">
                    No hay transacciones registradas.
                </p>
            </div>
        );
    }

    return (
        <div className="transaction-history">
            <div className="history-list">
                {transactions.map((transaction) => (
                    <div
                        className="transaction-card"
                        key={transaction.id}
                    >
                        <div className="transaction-icon">
                            $
                        </div>

                        <div className="transaction-info">
                            <div className="transaction-accounts">
                                <strong>
                                    {getCustomerName(
                                        transaction.senderAccountNumber
                                    )}
                                </strong>

                                <span className="transaction-arrow">
                                    →
                                </span>

                                <strong>
                                    {getCustomerName(
                                        transaction.receiverAccountNumber
                                    )}
                                </strong>
                            </div>

                            <div className="transaction-numbers">
                                {transaction.senderAccountNumber}
                                <span>→</span>
                                {transaction.receiverAccountNumber}
                            </div>
                        </div>

                        <div className="transaction-amount">
                            <span>TRANSFERENCIA</span>

                            <strong>
                                $
                                {Number(
                                    transaction.amount
                                ).toLocaleString("es-CO", {
                                    minimumFractionDigits: 2,
                                    maximumFractionDigits: 2,
                                })}
                            </strong>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default TransactionHistory;
