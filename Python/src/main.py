import tkinter as tk
from tkinter import font, messagebox, Button
import os
from baseDatos.deserializador import Deserializador
from baseDatos.serializador import Serializador
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.suscripcion import Suscripcion
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco

# FAVOR SER ORDENADOS CON EL CÓDIGO Y COMENTAR TODO BIEN. RESPETAR EL FORMATO DE COMENTARIOS


class App():

    welcomewindow = None
    image_index = 0  # Variable para realizar un seguimiento del índice del pack de imagenes de los desarrolladores
    # ----------------- VENTANA INICIAL --------------------------

    @classmethod
    def start_initial_window(cls):

        # Métodos de funcionamiento de la ventana de inicio
        def exit_initial_window():
            welcomewindow.destroy()

        def show_description():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")

        def login(event):
            # name_email_user = str(user_email_entry.get())
            # password_user = str(password_entry.get())
            # possible_user = Usuario.verificarCredenciales(name_email_user, password_user)
            # if(isinstance(possible_user, Usuario)):
            #     #PONER CÓDIGO PARA SEGUIR EN LA VENTANA DE APLICACIÓN
            #     pass
            # else:
            #     messagebox.showerror("Mis finanzas", "Error: No se encuentra un usuario con estos datos. Inténtelo de nuevo.")
            #     password_entry.delete(0, tk.END)
            #     user_email_entry.delete(0, tk.END)
            exit_initial_window()
            App.start_main_window()

        def format_entry_user_email(event):
            if int(event.type) == 9:
                user_email_entry.config(bg="gray", fg="black")
            elif int(event.type) == 10:
                user_email_entry.config(fg="white", bg="black")

        def format_entry_password(event):
            if int(event.type) == 9:
                password_entry.config(bg="gray", fg="black")
            elif int(event.type) == 10:
                password_entry.config(fg="white", bg="black")

        def change_button_text():
            # Poner estilo particular para cada uno
            if button_developers_text.get()[0:1] == "1":
                button_developers_text.set(
                    "2. Leonard David Vivas Dallos.\n PONER HOJA DE VIDA")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            elif button_developers_text.get()[0:1] == "2":
                button_developers_text.set(
                    "3. José Daniel Moreno Ceballos.\n PONER HOJA DE VIDA")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            elif button_developers_text.get()[0:1] == "3":
                button_developers_text.set(
                    "4. Jorge Humberto García Botero.\n PONER HOJA DE VIDA")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            elif button_developers_text.get()[0:1] == "4":
                button_developers_text.set("5. Juan Pablo Mejía Gómez.\nSoy un programador altamente motivado y apasionado por el desarrollo de software. Tengo sólidos conocimientos en lenguajes de programación como Python, Java y HTML, así como experiencia en el diseño y desarrollo de aplicaciones móviles y web. Poseo habilidades en resolución de problemas, colaboración en equipo y un enfoque orientado a resultados.")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            elif button_developers_text.get()[0:1] == "5":
                button_developers_text.set(
                    "1. Tomas Escobar Rivera.\n PONER HOJA DE VIDA")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            update_image()

        def update_image():
            # Añadir las rutas para cada imágen
            pablo_1 = os.path.join(current_directory +
                                   "\static\pablo_photos", "1.png")
            pablo_2 = os.path.join(current_directory +
                                   "\static\pablo_photos", "2.png")
            pablo_3 = os.path.join(current_directory +
                                   "\static\pablo_photos", "3.png")
            pablo_4 = os.path.join(current_directory +
                                   "\static\pablo_photos", "4.png")

            image_paths = [
                # Pack de imagenes 1
                route_logo,
                route_image,
                route_logo,
                route_logo,
                # Pack de imagenes 2
                route_logo,
                route_image,
                route_logo,
                route_logo,
                # Pack de imagenes 3
                route_logo,
                route_logo,
                route_image,
                route_logo,
                # Pack de imagenes 4
                route_logo,
                route_logo,
                route_logo,
                route_image,
                # Pack de imagenes 5
                pablo_1,
                pablo_2,
                pablo_3,
                pablo_4,
            ]

            # Borrar cualquier sub-frame existente
            for subframe in bottomrightframe.winfo_children():
                subframe.destroy()

            # Divide el marco inferior derecho en dos filas y dos columnas.
            for i in range(0, 2):
                bottomrightframe.rowconfigure(i, weight=1)
                bottomrightframe.columnconfigure(i, weight=1)

            # Crear sub-frames para las imágenes superiores
            for i in range(0, 2):
                # Calcular el índice de la imagen actual
                current_image_index = (cls.image_index + i) % len(image_paths)

                # Cargar la imagen actual
                image_path = image_paths[current_image_index]
                image = tk.PhotoImage(file=image_path)

                # Crear un sub-frame para mostrar la imagen
                subframe = tk.Frame(
                    bottomrightframe, bg="white", borderwidth=1, relief="groove")
                subframe.grid(row=0, column=i, sticky="nsew")

                # Crear un label para mostrar la imagen
                image_label = tk.Label(subframe, image=image, cursor="cross")
                image_label.image = image
                image_label.pack(expand=True, fill="both")

            # Crear sub-frame para las imágenes inferiores
            for i in range(0, 2):
                # Calcular el índice de la imagen actual
                current_image_index = (
                    cls.image_index + i + 2) % len(image_paths)

                # Cargar la imagen actual
                image_path = image_paths[current_image_index]
                image = tk.PhotoImage(file=image_path)

                # Crear un sub-frame para mostrar la imagen
                subframe = tk.Frame(
                    bottomrightframe, bg="white", borderwidth=1, relief="groove")
                subframe.grid(row=1, column=i, sticky="nsew")

                # Crear un label para mostrar la imagen
                image_label = tk.Label(subframe, image=image, cursor="cross")
                # Keep a reference to the image to prevent it from being garbage collected
                image_label.image = image
                image_label.pack(expand=True, fill="both")

            # Incrementar el índice de la imagen
            cls.image_index = (cls.image_index + 4) % len(image_paths)

        # Función para cambiar la imagen al hacer clic
        def cambiar_imagen_sistema(event):
            nonlocal current_image_index
            current_image_index = (current_image_index + 1) % len(images)
            current_image = images[current_image_index]
            system_image_label.config(image=current_image)

        # Configuración básica de parámetros de la ventana de inicio
        welcomewindow = tk.Tk()
        welcomewindow.geometry("1000x800")
        welcomewindow.title("Mis Finanzas")
        welcomewindow.resizable(0, 0)
        current_directory = os.path.dirname(os.path.abspath(__file__))
        route_logo = os.path.join(current_directory + "\static", "logo.png")
        logo = tk.PhotoImage(file=route_logo)
        welcomewindow.iconphoto(True, logo)

        # Configuración básica de los parámetros del mainframe en la ventana de inicio
        mainframe = tk.Frame(welcomewindow, bg="#DFDEDE")
        mainframe.pack(fill="both", expand=True)

        # Configuración de los sub-frames anidados al mainframe de la ventana de inicio
        upperframe = tk.Frame(mainframe, bg="black",
                              borderwidth=1, relief="solid")
        upperframe.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

        # Configuración de menú de inicio
        home_menu = tk.Menu(upperframe, cursor="cross")
        menu_options = tk.Menu(home_menu, tearoff=0)
        menu_options.add_command(label="Salir de la aplicación", command=exit_initial_window,
                                 activebackground="gray", activeforeground="white")
        menu_options.add_command(label="Descripción del sistema", command=show_description,
                                 activebackground="gray", activeforeground="white")
        menu_options.add_separator()
        menu_options.add_command(
            label="Guardar objetos", activebackground="gray", activeforeground="white")
        menu_options.add_command(
            label="Cargar objetos", activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Inicio", menu=menu_options,
                              activebackground="gray", activeforeground="white")
        welcomewindow.config(menu=home_menu)

        leftframe = tk.Frame(mainframe, bg="white",
                             borderwidth=1, relief="solid")
        leftframe.place(anchor="w", relheight=0.85,
                        relwidth=0.46, rely=0.55, relx=0.03)

        rightframe = tk.Frame(mainframe, bg="white",
                              borderwidth=1, relief="solid")
        rightframe.place(anchor="e", relheight=0.85,
                         relwidth=0.46, relx=0.97, rely=0.55)

        # Configuración de los sub-frames anidados a cada uno de los sub-frames anidados a mainframe de la ventana de inicio
        upperleftframe = tk.Frame(
            leftframe, bg="white", borderwidth=1, relief="groove")
        upperleftframe.place(anchor="n", relheight=0.35,
                             relwidth=0.993, rely=0.002, relx=0.5)

        bottomleftframe = tk.Frame(
            leftframe, bg="white", borderwidth=1, relief="groove")
        bottomleftframe.place(anchor="s", relheight=0.642,
                              relwidth=0.993, rely=0.998, relx=0.5)

        upperrightframe = tk.Frame(
            rightframe, bg="white", borderwidth=1, relief="groove")
        upperrightframe.place(anchor="n", relheight=0.35,
                              relwidth=0.993, rely=0.002, relx=0.5)

        bottomrightframe = tk.Frame(
            rightframe, bg="white", borderwidth=1, relief="groove")
        bottomrightframe.place(anchor="s", relheight=0.642,
                               relwidth=0.993, rely=0.998, relx=0.5)

        # Configuración de los nodos que se ubicarán en los sub-frames anidados a cada uno de los sub-frames anidados a mainframe de la ventana de inicio

        # -------Texto de título(upperframe)---------------------
        upper_label_text_variable = "Mis Finanzas"
        upper_text_font_style = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        title_label = tk.Label(upperframe, text=upper_label_text_variable, fg="white",
                               bg="gray", wraplength=400, font=upper_text_font_style, width=50)
        title_label.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        # --------------------------------------------------
        # -------Imágen del título(upperframe)---------------------
        route_image = os.path.join(current_directory + "\static", "unal.png")
        upper_image = tk.PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = tk.Label(upperframe, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)
        # --------------------------------------------------
        # -------Texto de bienvenida(P3 - upperleftframe)---------------------
        welcome_label_text_variable = "Bienvenidos al sistema de gestión financiera Mis Finanzas programado por: \n->Juan Pablo Mejía Gómez.\n->Leonard David Vivas Dallos.\n->José Daniel Moreno Ceballos.\n->Tomás Escobar Rivera.\n->Jorge Humberto García Botero."
        welcome_label = tk.Text(upperleftframe, cursor="cross", fg="black", bg="white", font=(
            "Alegreya Sans", 12), wrap="word", spacing1=8, border=0)
        welcome_label.insert(tk.INSERT, welcome_label_text_variable)
        welcome_label.tag_configure("justifying", justify="center")
        welcome_label.tag_add("justifying", "1.0", tk.END)
        welcome_label.config(state="disabled")
        welcome_label.pack(expand=True, fill="both",
                           anchor="s", padx=1, pady=20)
        # --------------------------------------------------
        # -------Hoja de vida de los desarrolladores(P5 - upperrightframe)---------------------
        # Crear el botón y asociar la función change_button_text con él
        button_developers_text = tk.StringVar(
            upperrightframe, "1. Tomas Escobar Rivera.\n PONER HOJA DE VIDA")
        button_developers = Button(upperrightframe, textvariable=button_developers_text, bg="white", command=change_button_text, font=(
            "Alegreya Sans", 12), activebackground="gray", activeforeground="white", border=1, relief="groove", cursor="cross", wraplength=450)
        style = font.Font(family="Times New Roman", size=12)
        button_developers.config(
            font=style, bg="#f8e5c7", border=2, relief="raised")
        button_developers.pack(expand=True, fill="both")
        # --------------------------------------------------
        # -------Fotos de los desarrolladores(P6 - bottomrightframe)---------------------
        image_label = tk.Label(bottomrightframe, cursor="cross")
        image_label.pack(expand=True, fill="both")
        update_image()
        # --------------------------------------------------
        # -------Imágenes asociadas al sistema(P4 - bottomleftframe)---------------------
        # Crear un label para mostrar la imagen.
        system_image_label = tk.Label(
            bottomleftframe, border=2, relief="groove", cursor="cross")
        system_image_label.place(
            anchor="n", relheight=.5, relwidth=.99, relx=0.5, rely=0.01)

        # Lista de las imágenes asociadas al sistema
        image_paths = [
            route_logo,
            route_image,
            route_logo,
            route_image,
            route_logo
        ]

        # Cargar las imágenes
        images = [tk.PhotoImage(file=image_path) for image_path in image_paths]

        # Mostrar la imagen inicial
        current_image_index = 0
        current_image = images[current_image_index]
        system_image_label.config(image=current_image)

        # Vincular el evento de poner el ratón sobre el label de la imagen
        system_image_label.bind("<Enter>", cambiar_imagen_sistema)
        # --------------------------------------------------

        # -------Interfaz de acceso al sistema(P4 - bottomleftframe)---------------------
        # Crear un label para inicio de sesión.
        style = font.Font(family="Times New Roman", size=14)
        login_label = tk.Label(bottomleftframe, text="Ingresa tus datos para iniciar sesión: ",
                               fg="white", bg="black", border=1, relief="sunken", font=style)
        login_label.place(anchor="n", relheight=.1,
                          relwidth=.99, relx=0.5, rely=0.51)
        # Crear un label con el usuario ó el correo.
        user_email_label = tk.Label(bottomleftframe, text="Usuario: ",
                                    fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_label.place(anchor="n", relheight=.20,
                               relwidth=.3, relx=0.156, rely=0.61)
        # Crear un entry para recibir el usuario ó el correo del usuario.
        user_email_entry = tk.Entry(
            bottomleftframe, fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_entry.place(anchor="n", relheight=.20,
                               relwidth=.45, relx=0.5, rely=0.61)
        # Crear un label con el usuario ó el correo.
        password_label = tk.Label(bottomleftframe, text="Contraseña: ",
                                  fg="white", bg="black", border=1, relief="sunken", font=style)
        password_label.place(anchor="n", relheight=.19,
                             relwidth=.3, relx=0.156, rely=0.81)
        # Crear un entry para recibir la contraseña del usuario.
        password_entry = tk.Entry(
            bottomleftframe, fg="white", bg="black", border=1, relief="sunken", font=style)
        password_entry.place(anchor="n", relheight=.19,
                             relwidth=.45, relx=0.5, rely=0.81)
        # Crear un botón para iniciar sesión.
        login_button = tk.Button(bottomleftframe, fg="white", bg="black", border=1, relief="sunken",
                                 font=style, text="Ingresar", activebackground="gray", activeforeground="black")
        login_button.place(anchor="s", relheight=.39,
                           relwidth=.269, relx=0.860, rely=0.9999999)
        login_button.bind("<Button-1>", login)
        # Formato especial a los entry de forma dinámica
        user_email_entry.bind('<FocusIn>', format_entry_user_email)
        user_email_entry.bind('<FocusOut>', format_entry_user_email)
        password_entry.bind('<FocusIn>', format_entry_password)
        password_entry.bind('<FocusOut>', format_entry_password)

        welcomewindow.mainloop()
    # --------------------------------------------------

    # ----------------- VENTANA PRINCIPAL --------------------------
    @classmethod
    def start_main_window(cls):
        # Métodos de funcionamiento de la ventana Principal

        def exit_principal_window():
            principalWindow.destroy()

        # Metodos de las funcionalidades del menú

        def modificarSuscripcion():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Modificar Suscripcion")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo modificarSuscripcion y agregar aca el funcionamiento de su funcionalidad")

        def invertirSaldo():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Invertir Saldo")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo invertirSaldo y agregar aca el funcionamiento de su funcionalidad")

        def consignarSaldo():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="consignarSaldo")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo consignarSaldo y agregar aca el funcionamiento de su funcionalidad")

        def transferirSaldo():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Transferir Saldo")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo transferirSaldo y agregar aca el funcionamiento de su funcionalidad")

        def compraCorriente():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Comprar con cuenta corriente")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo compraCorriente y agregar aca el funcionamiento de su funcionalidad")

        def modificarSuscripcion():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Modificar Suscripcion")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo modificarSuscripcion y agregar aca el funcionamiento de su funcionalidad")

        def asesoramientoInversiones():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Asesoramiento de Inversiones")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo asesoramientoInversiones y agregar aca el funcionamiento de su funcionalidad")

        def modificarSuscripcion():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Modificar Suscripcion")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo modificarSuscripcion y agregar aca el funcionamiento de su funcionalidad")

        def compraCartera():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Compra Catera")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo compraCartera y agregar aca el funcionamiento de su funcionalidad")

        def calculadoraFinanciera():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Calculadora Financiera")
            labelDescripcion.config(
                text="Agregar la descripcion en el metodo calculadoraFinanciera y agregar aca el funcionamiento de su funcionalidad")

        def pedirPrestamo():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Pedir Prestamo")
            labelDescripcion.config(
                text="¿Necesitas dinero? realiza un prestamo con tu banco.\nPara pedir un prestamo es necesario que cuentes con una cuenta de ahorros, la cantidad de dinero que puedes prestar va a depender de tu nivel de suscripción y del banco asociado a tu cuenta")

        def pagarPrestamo():
            # Editar la descripcion de su funcionalidad
            labelTitulo.config(text="Pagar Prestamo")
            labelDescripcion.config(
                text="Es importante pagar tus deudas para poder confiando en ti.\n En esta sección puedes pagar tus prestamos, si no has realizado ningun prestamos y quieres hacerlo ingresa a la sección Pedir Prestamo")

        # Configuración básica de parámetros de la ventana Principal
        principalWindow = tk.Tk()
        principalWindow.geometry("1000x800")
        principalWindow.title("Mis Finanzas")
        # principalWindow.resizable(0,0)
        current_directory = os.path.dirname(os.path.abspath(__file__))
        route_logo = os.path.join(current_directory + "\static", "logo.png")
        logo = tk.PhotoImage(file=route_logo)
        principalWindow.iconphoto(True, logo)

        tituloFuncionalidad = "Mis Finanzas"
        # Configuración básica de los parámetros del mainframe en la ventana Principal
        mainframe = tk.Frame(principalWindow, bg="#DFDEDE")
        mainframe.pack(fill="both", expand=True)

        # Configuración de los subs-frames

        # subframe del titulo
        subframe0 = tk.Frame(mainframe, bg="black",
                             borderwidth=1, relief="solid")
        subframe0.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

        subframeAplicacion = tk.Frame(
            mainframe, bg="white", borderwidth=1, relief="solid")
        subframeAplicacion.place(
            relheight=0.85, relwidth=0.94, rely=0.15, relx=0.03)

        # -------Menu---------------------
        proceso1 = "Modificar Suscripcion"
        proceso2 = "Invertir saldo de mi cuenta"
        proceso3 = "Consignar saldo a mi cuenta"
        proceso4 = "Transferir saldo entre cuentas"
        proceso5 = "Compra con cuenta Corriente"
        proceso6 = "Gestionar Prestamos"
        proceso7 = "Asesoramiento de inversiones"
        proceso8 = "Compra de cartera"
        proceso9 = "Calculadora Financiera"

        home_menu = tk.Menu(subframe0, cursor="cross")

        archivo = tk.Menu(home_menu, tearoff=0)
        archivo.add_command(label="Aplicación",
                            activebackground="gray", activeforeground="white")
        archivo.add_command(label="Salir", command=exit_principal_window,
                            activebackground="gray", activeforeground="white")
    # ----------Menu procesos y consultas
        procesos = tk.Menu(home_menu, tearoff=0)
    # ----------sub menus de procesos
        prestamos_menu = tk.Menu(procesos, tearoff=0)
        prestamos_menu.add_command(label="Pedir Prestamos", command=pedirPrestamo,
                                   activebackground="gray", activeforeground="white")
        prestamos_menu.add_command(label="Pagar Prestamos", command=pagarPrestamo,
                                   activebackground="gray", activeforeground="white")

    # Agregamos los submenus a procesos
        procesos.add_command(label=proceso1, command=modificarSuscripcion,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso2, command=invertirSaldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso3, command=consignarSaldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso4, command=transferirSaldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso5, command=compraCorriente,
                             activebackground="gray", activeforeground="white")
        procesos.add_cascade(label=proceso6, menu=prestamos_menu,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso7, command=asesoramientoInversiones,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso8, command=compraCartera,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso9, command=calculadoraFinanciera,
                             activebackground="gray", activeforeground="white")

        home_menu.add_cascade(label="Archivo", menu=archivo,
                              activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Procesos y Consultas", menu=procesos,
                              activebackground="gray", activeforeground="white")

        principalWindow.config(menu=home_menu)

        # -------Texto de título(subframe0)---------------------

        tituloFontStyle = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        labelTitulo = tk.Label(subframe0, text=tituloFuncionalidad, fg="white",
                               bg="gray", wraplength=400, font=tituloFontStyle, width=50)
        labelTitulo.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        # -------Imagen del titulo(subframe0)
        route_image = os.path.join(current_directory + "\static", "unal.png")
        upper_image = tk.PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = tk.Label(subframe0, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)

        # ------------Descripcion de la funcionalidad
        subframeDescripcion = tk.Frame(
            subframeAplicacion, bg="gray", borderwidth=1, relief="solid")
        subframeDescripcion.place(
            relheight=0.2, relwidth=1, rely=0.0, relx=0.0)
        descripcionFontStyle = font.Font(size=12, family="Alegreya Sans")
        descripcionFuncionalidad = "Ad cillum enim occaecat aliqua ad ad sit. Reprehenderit laboris elit veniam minim esse elit. Anim deserunt officia irure proident non velit duis sint quis aute Lorem id."
        labelDescripcion = tk.Label(subframeDescripcion, text=descripcionFuncionalidad,
                                    fg="white", bg="gray", wraplength=400, font=descripcionFontStyle, width=50)
        labelDescripcion.place(anchor="w", relwidth=1, rely=0.5, relx=0.001)

        principalWindow.mainloop()
    # --------------------------------------------------


if __name__ == "__main__":
    # Poner código para ejecutar la interfaz
    App().start_initial_window()
