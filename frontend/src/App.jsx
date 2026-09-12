import { useEffect, useState } from "react";
import CustomerList from "./components/CustomerList";
import TransferForm from "./components/TransferForm";
import TransactionHistory from "./components/TransactionHistory";
import CreateAccountForm from "./components/CreateAccountForm";
import "./App.css";

function App() {
    const [customers, setCustomers] = useState([]);

    const [showTransfer, setShowTransfer] = useState(false);
    const [showHistory, setShowHistory] = useState(false);
    const [showCreateAccount, setShowCreateAccount] = useState(false);

    const loadCustomers = async () => {
        try {
            const response = await fetch(
                "http://localhost:8080/api/customers"
            );

            if (!response.ok) {
                throw new Error(
                    "No fue posible obtener los clientes"
                );
            }

            const data = await response.json();

            setCustomers(data);

        } catch (error) {
            console.error(
                "Error cargando clientes:",
                error
            );
        }
    };

    useEffect(() => {
        loadCustomers();
    }, []);

    /* -----------------------------
       TRANSFERENCIA
    ----------------------------- */

    const handleTransferComplete = () => {
        loadCustomers();

        setShowTransfer(false);
    };

    const handleTransferButton = () => {
        setShowTransfer(!showTransfer);

        setShowHistory(false);
        setShowCreateAccount(false);
    };

    /* -----------------------------
       HISTORIAL
    ----------------------------- */

    const handleHistoryButton = () => {
        setShowHistory(!showHistory);

        setShowTransfer(false);
        setShowCreateAccount(false);
    };

    /* -----------------------------
       CREAR CLIENTE
    ----------------------------- */

    const handleCreateAccountButton = () => {
        setShowCreateAccount(!showCreateAccount);

        setShowTransfer(false);
        setShowHistory(false);
    };

    const handleCustomerCreated = () => {
        loadCustomers();

        setShowCreateAccount(false);
    };

    return (
        <div className="app">

            {/* HEADER */}

            <header className="header">

                <div className="header-content">

                    <div className="brand">

                        <div className="brand-icon">
                            B
                        </div>

                        <div>
                            <h1>Banco UdeA</h1>

                            <p>
                                Sistema bancario
                            </p>
                        </div>

                    </div>

                </div>

            </header>

            {/* CONTENIDO */}

            <main className="main-container">

                {/* PANEL PRINCIPAL */}

                <section className="welcome-section">

                    <div>

                        <h2>
                            Panel bancario
                        </h2>

                        <p>
                            Consulta los saldos y administra
                            las cuentas de tus clientes.
                        </p>

                    </div>

                    <div className="action-buttons">

                        {/* TRANSFERENCIA */}

                        <button
                            className={
                                showTransfer
                                    ? "secondary-button active"
                                    : "primary-button"
                            }
                            onClick={handleTransferButton}
                        >
                            {showTransfer
                                ? "Cerrar transferencia"
                                : "Realizar transferencia"}
                        </button>

                        {/* HISTORIAL */}

                        <button
                            className={
                                showHistory
                                    ? "secondary-button active"
                                    : "secondary-button"
                            }
                            onClick={handleHistoryButton}
                        >
                            {showHistory
                                ? "Cerrar historial"
                                : "Ver historial"}
                        </button>

                        {/* CREAR CLIENTE */}

                        <button
                            className={
                                showCreateAccount
                                    ? "secondary-button active"
                                    : "secondary-button"
                            }
                            onClick={
                                handleCreateAccountButton
                            }
                        >
                            {showCreateAccount
                                ? "Cerrar formulario"
                                : "Nuevo cliente"}
                        </button>

                    </div>

                </section>

                {/* FORMULARIO DE TRANSFERENCIA */}

                {showTransfer && (

                    <section className="transfer-section">

                        <TransferForm
                            customers={customers}
                            onTransferComplete={
                                handleTransferComplete
                            }
                        />

                    </section>

                )}

                {/* HISTORIAL */}

                {showHistory && (

                    <section className="customers-section history-section">

                        <div className="section-header">

                            <div>

                                <h2>
                                    Historial de transacciones
                                </h2>

                                <p>
                                    Movimientos realizados
                                    entre las cuentas.
                                </p>

                            </div>

                            <span className="account-count">
                                Transacciones
                            </span>

                        </div>

                        <TransactionHistory
                            customers={customers}
                        />

                    </section>

                )}

                {/* CREAR CLIENTE */}

                {showCreateAccount && (

                    <section className="transfer-section">

                        <CreateAccountForm
                            onCustomerCreated={
                                handleCustomerCreated
                            }
                        />

                    </section>

                )}

                {/* CUENTAS */}

                <section className="customers-section">

                    <div className="section-header">

                        <div>

                            <h2>
                                Cuentas
                            </h2>

                            <p>
                                Estado actual de las cuentas
                                bancarias.
                            </p>

                        </div>

                        <span className="account-count">
                            {customers.length} cuentas
                        </span>

                    </div>

                    <CustomerList
                        customers={customers}
                    />

                </section>

            </main>

            {/* FOOTER */}

            <footer className="footer">

                <p>
                    Banco UdeA · Arquitectura de Software
                </p>

            </footer>

        </div>
    );
}

export default App;
