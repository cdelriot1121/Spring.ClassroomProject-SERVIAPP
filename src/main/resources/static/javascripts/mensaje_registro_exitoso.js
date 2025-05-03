document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formRegistro");
    const emailInput = document.getElementById("email");
    const otpInput = document.getElementById("otpInput");
    const otpButton = document.getElementById("otpButton");
    const reenviarLink = document.getElementById("reenviarOtpLink");

    let otpEnviado = false;
    let otpVerificado = false;

    const mostrarAlerta = ({ icon, title, text }) => {
        Swal.fire({
            icon,
            title,
            text,
            confirmButtonColor: '#3085d6',
            background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
            customClass: {
                popup: 'swal-custom-popup'
            },
            didRender: () => {
                const logo = document.createElement('img');
                logo.src = '/img_local/logo-pagina-serviapp.png';
                logo.alt = 'Logo de ServiApp';
                logo.style.width = '155px';
                logo.style.display = 'block';
                logo.style.margin = '0 auto 5px';
                Swal.getIcon().before(logo);
            }
        });
    };

    otpButton.addEventListener("click", async () => {
        const email = emailInput.value.trim();
        const nombre = form.nombre.value.trim();
        const password = form.password.value.trim();

        if (!email || !nombre || !password) {
            return mostrarAlerta({
                icon: 'warning',
                title: 'Campos incompletos',
                text: 'Completa todos los campos antes de continuar.'
            });
        }

        if (!otpEnviado) {
            const existe = await fetch(`/usuarios/verificar-email?email=${email}`).then(r => r.json());
            if (existe) {
                return mostrarAlerta({
                    icon: 'error',
                    title: 'Correo en uso',
                    text: 'El correo electrónico ya está registrado.'
                });
            }

            const resp = await fetch(`/otp/enviar?email=${email}`, { method: 'POST' });
            if (resp.ok) {
                otpEnviado = true;
                otpInput.style.display = "block";
                reenviarLink.style.display = "block";
                otpButton.textContent = "Verificar Código";
                mostrarAlerta({
                    icon: 'info',
                    title: 'Código enviado',
                    text: 'Hemos enviado un código de verificación a tu correo electrónico.'
                });
            } else {
                mostrarAlerta({
                    icon: 'error',
                    title: 'Error de envío',
                    text: 'No se pudo enviar el código  de verificación. Intenta de nuevo.'
                });
            }

        } else if (!otpVerificado) {
            const otp = otpInput.value.trim();
            if (!otp.match(/^\d{6}$/)) {
                return mostrarAlerta({
                    icon: 'warning',
                    title: 'Código inválido',
                    text: 'El código debe tener 6 dígitos numéricos.'
                });
            }

            const res = await fetch(`/otp/verificar?email=${email}&otp=${otp}`);
            if (res.ok) {
                const ok = await res.json();
                if (ok) {
                    otpVerificado = true;
                    otpButton.textContent = "Registrar";
                    otpInput.disabled = true;
                    mostrarAlerta({
                        icon: 'success',
                        title: 'Código verificado',
                        text: 'Tu correo ha sido validado correctamente.'
                    });
                } else {
                    mostrarAlerta({
                        icon: 'error',
                        title: 'Código de verificación inválido',
                        text: 'El código de verificación ingresado no es válido o ha expirado.'
                    });
                }
            } else {
                mostrarAlerta({
                    icon: 'error',
                    title: 'Error de validación',
                    text: 'No se pudo verificar el código. Intenta de nuevo.'
                });
            }

        } else {
            const formData = new FormData(form);
            const obj = Object.fromEntries(formData);

            const resp = await fetch("/usuarios/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(obj),
            });

            if (resp.ok) {
                Swal.fire({
                    icon: 'success',
                    title: `¡Registro exitoso, ${obj.nombre}!`,
                    text: 'Bienvenido a ServiApp. Tu cuenta se ha creado correctamente.',
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'Aceptar',
                    background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
                    customClass: {
                        popup: 'swal-custom-popup'
                    },
                    didRender: () => {
                        const logo = document.createElement('img');
                        logo.src = '/img_local/logo-pagina-serviapp.png';
                        logo.alt = 'Logo de ServiApp';
                        logo.style.width = '155px';
                        logo.style.display = 'block';
                        logo.style.margin = '0 auto 5px';
                        Swal.getIcon().before(logo);
                    }
                }).then(() => {
                    window.location.href = "/login";
                });
            } else {
                mostrarAlerta({
                    icon: 'error',
                    title: 'Registro fallido',
                    text: 'No se pudo completar el registro.'
                });
            }
        }
    });

    reenviarLink.addEventListener("click", async () => {
        const email = emailInput.value.trim();
        const resp = await fetch(`/otp/enviar?email=${email}`, { method: 'POST' });
        if (resp.ok) {
            mostrarAlerta({
                icon: 'info',
                title: 'Código reenviado',
                text: 'Hemos reenviado un nuevo código de verificación a tu correo.'
            });
        } else {
            mostrarAlerta({
                icon: 'error',
                title: 'Reenvío fallido',
                text: 'No se pudo reenviar el código de verificación.'
            });
        }
    });



    const togglePassword = document.getElementById("togglePassword");
    if (togglePassword) {
        togglePassword.addEventListener("click", () => {
            const passInput = document.getElementById("password");
            const icon = togglePassword.querySelector("i");
            if (passInput.type === "password") {
                passInput.type = "text";
                icon.classList.remove("fa-eye");
                icon.classList.add("fa-eye-slash");
            } else {
                passInput.type = "password";
                icon.classList.remove("fa-eye-slash");
                icon.classList.add("fa-eye");
            }
        });
    
    }
});