import tkinter as tk
from tkinter import font, messagebox, Button, StringVar
from tkinter.ttk import Combobox
import os
from excepciones import banksException, suscriptionException, usersException
from baseDatos.deserializador import Deserializador
from baseDatos.serializador import Serializador
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.suscripcion import Suscripcion
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco
from gestorAplicación.interno.corriente import Corriente
from gestorAplicación.interno.ahorros import Ahorros
from gestorAplicación.externo.divisas import Divisas
from gestorAplicación.externo.tablas import Tablas
from gestorAplicación.externo.cuotas import Cuotas

# FAVOR SER ORDENADOS CON EL CÓDIGO Y COMENTAR TODO BIEN. USAR SNAKECASE. NOMBRAR VARIABLES Y MÉTODOS EN INGLÉS. CODIFICAR EXCEPCIONES EN EL PAQUETE EXCEPCIONES

# ------ FIELD FRAME PARA DIÁLOGOS DE TEXTO --------
class FieldFrame(tk.Frame):
    def __init__(self, tituloCriterios, criterios, tituloValores,**kwargs):
        self.setTituloCriterios(tituloCriterios)
        self.setCriterios(criterios)
        self.setTituloValores(tituloValores)
        self.valores = []
        self.habilitado = []

        for key in kwargs:
            if key == "valores":
                self.setValores(kwargs[key])
            if key == "habilitado":
                self.setHabilitado(kwargs[key])

        field_frame = tk.Frame(App.getSubframeMain(), bg="white", borderwidth=1, relief="solid")
        field_frame.place(
            relheight=0.75, relwidth=0.6, rely=0.25, relx=0.2)
        title_style = font.Font(family="Times New Roman", size=13, weight="bold")
        criteria_style = font.Font(family="Times New Roman", size=13, underline=1)
        entry_style = font.Font(family="Times New Roman", size=13)

        title_criteria = tk.Label(master=field_frame, textvariable = self.tituloCriterios, width=35, bg="white", fg="black", font=title_style, border=1, relief="ridge")
        title_criteria.grid(row=0, column=0, padx=3, pady=3)
        title_value = tk.Label(master=field_frame, textvariable = self.tituloValores, width=35, bg="white", fg="black", font=title_style, border=1, relief="ridge")
        title_value.grid(row=0, column=1, padx=3, pady=3)

        for i in range(0, len(self.getCriterios())):
            entry = tk.Entry(master=field_frame, width=35, bg="white", fg="black", font=entry_style, border=1, relief="groove")
            label = tk.Label(master=field_frame, text = self.getCriterios()[i], width=35, bg="white", fg="black", font=criteria_style, border=1, relief="groove")
    
            if(self.getValores() != None):
                try:
                    entry.insert(0, self.getValores()[i])
                except:
                    pass
            if(self.getCriterios()[i] in self.getHabilitado()):
                entry.config(state="disabled")

            entry.grid(column=1, row=i + 1, padx=3, pady=3)
            label.grid(column=0, row=i + 1, padx=3, pady=3)

    #Gets & Sets
    def setValue(self, criterio, value):
        self.criterios[criterio] = value
    def getValue(self, criterio):
        return self.criterios[criterio]
    def setTituloCriterios(self, tituloCriterios):
        self.tituloCriterios = tk.StringVar()
        self.tituloCriterios.set(tituloCriterios)
    def getTituloCriterios(self):
        return self.tituloCriterios
    def setCriterios(self, criterios):
        self.criterios = criterios
    def getCriterios(self):
        return self.criterios
    def setTituloValores(self, tituloValores):
        self.tituloValores = tk.StringVar()
        self.tituloValores.set(tituloValores) 
    def getTituloValores(self):
        return self.tituloValores
    def setValores(self, valores):
        self.valores = valores
    def getValores(self):
        return self.valores
    def setHabilitado(self, habilitado):
        self.habilitado = habilitado
    def getHabilitado(self):
        return self.habilitado
# --------------------------------------------------

# ----------------- APP ----------------
class App():
    # Guardar objetos al sistema
    """ user1 = Usuario(_nombre="pepe", _correo="pepe@mail", _contrasena="123", _suscripcion=Suscripcion.BRONCE)
    Serializador.serializar([user1]) """
        
    estado1 = Estado()
    Serializador.serializar([estado1])
    banco1 = Banco(estado=estado1)
    banco2 = Banco(estado=estado1, nombre="Banco prueba 1")
    banco3 = Banco(estado=estado1, nombre="Banco prueba 2")
    Serializador.serializar([banco1, banco2, banco3])
    user1 = Usuario(_nombre="Jaime Guzman", _correo="JaimeGuzman@mail", _contrasena="12345", _suscripcion=Suscripcion.BRONCE)
    cuenta3 = Ahorros(banco = banco1, clave = 1234, nombre = "Cuenta ahorros prueba", divisa = Divisas.COP, saldo = 100)
    cuenta1 = Corriente(banco = banco1, clave = 1234, nombre = "Visa", divisa = Divisas.COP)
    cuenta2 = Corriente(banco = banco1, clave = 1234, nombre = "Master", divisa = Divisas.COP)
    user1.asociarCuenta(cuenta1)
    user1.asociarCuenta(cuenta2)
    user1.asociarCuenta(cuenta3)

    

    Serializador.serializar([cuenta1, cuenta2, cuenta3])
    Serializador.serializar([user1])

    #print (cuenta1)

    # Cargar objetos al sistema
    Deserializador.deserializar("Usuarios")
    Deserializador.deserializar("Cuentas")
    Deserializador.deserializar("Estados")
    Deserializador.deserializar("Bancos")
    Deserializador.deserializar("Estados")

    # Variables de clase para funcionamiento de la app
    initial_window = None
    main_window = None
    user = None
    subframe_main = None
    image_index = 0  # Variable para realizar un seguimiento del índice del pack de imagenes de los desarrolladores
    conf = True

    # ----------------- VENTANA INICIAL ----------------
    @classmethod
    def start_initial_window(cls):

        # Métodos de funcionamiento de la ventana de inicio
        def exit_initial_window():
            cls.initial_window.destroy()

        def show_description():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")

        def login(event):
            name_email_user = str(user_email_entry.get())
            password_user = str(password_entry.get())
            possible_user = Usuario.verificarCredenciales(
                name_email_user, password_user)
            if (isinstance(possible_user, Usuario)):
                cls.user = possible_user
                exit_initial_window()
                App.start_main_window()
            else:
                messagebox.showerror(
                    "Mis finanzas", "Error: No se encuentra un usuario con estos datos. Inténtelo de nuevo.")
                password_entry.delete(0, tk.END)
                user_email_entry.delete(0, tk.END)

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
            for subframe in bottom_right_frame.winfo_children():
                subframe.destroy()

            # Divide el marco inferior derecho en dos filas y dos columnas.
            for i in range(0, 2):
                bottom_right_frame.rowconfigure(i, weight=1)
                bottom_right_frame.columnconfigure(i, weight=1)

            # Crear sub-frames para las imágenes superiores
            for i in range(0, 2):
                # Calcular el índice de la imagen actual
                current_image_index = (cls.image_index + i) % len(image_paths)

                # Cargar la imagen actual
                image_path = image_paths[current_image_index]
                image = tk.PhotoImage(file=image_path)

                # Crear un sub-frame para mostrar la imagen
                subframe = tk.Frame(
                    bottom_right_frame, bg="white", borderwidth=1, relief="groove")
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
                    bottom_right_frame, bg="white", borderwidth=1, relief="groove")
                subframe.grid(row=1, column=i, sticky="nsew")

                # Crear un label para mostrar la imagen
                image_label = tk.Label(subframe, image=image, cursor="cross")
                # Keep a reference to the image to prevent it from being garbage collected
                image_label.image = image
                image_label.pack(expand=True, fill="both")

            # Incrementar el índice de la imagen
            cls.image_index = (cls.image_index + 4) % len(image_paths)

        # Función para cambiar la imagen al hacer clic
        def change_system_image(event):
            nonlocal current_image_index
            current_image_index = (current_image_index + 1) % len(images)
            current_image = images[current_image_index]
            system_image_label.config(image=current_image)

        # Configuración básica de parámetros de la ventana de inicio
        cls.initial_window = tk.Tk()
        cls.initial_window.geometry("1000x800")
        cls.initial_window.title("Mis Finanzas")
        #cls.initial_window.resizable(0, 0)
        current_directory = os.path.dirname(os.path.abspath(__file__))
        route_logo = os.path.join(current_directory + "\static", "logo.png")
        logo = tk.PhotoImage(file=route_logo)
        cls.initial_window.iconphoto(True, logo)

        # Configuración básica de los parámetros del main_frame en la ventana de inicio
        main_frame = tk.Frame(cls.initial_window, bg="#DFDEDE")
        main_frame.pack(fill="both", expand=True)

        # Configuración de los sub-frames anidados al main_frame de la ventana de inicio
        upper_frame = tk.Frame(main_frame, bg="black",
                              borderwidth=1, relief="solid")
        upper_frame.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

        # Configuración de menú de inicio
        home_menu = tk.Menu(upper_frame, cursor="cross")
        menu_options = tk.Menu(home_menu, tearoff=0)
        menu_options.add_command(label="Descripción del sistema", command=show_description,
                                 activebackground="gray", activeforeground="white")
        menu_options.add_command(label="Salir de la aplicación", command=exit_initial_window,
                                 activebackground="gray", activeforeground="white")
        menu_options.add_separator()
        menu_options.add_command(
            label="Guardar objetos", activebackground="gray", activeforeground="white")
        menu_options.add_command(
            label="Cargar objetos", activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Inicio", menu=menu_options,
                              activebackground="gray", activeforeground="white")
        cls.initial_window.config(menu=home_menu)

        left_frame = tk.Frame(main_frame, bg="white",
                             borderwidth=1, relief="solid")
        left_frame.place(anchor="w", relheight=0.85,
                        relwidth=0.46, rely=0.55, relx=0.03)

        right_frame = tk.Frame(main_frame, bg="white",
                              borderwidth=1, relief="solid")
        right_frame.place(anchor="e", relheight=0.85,
                         relwidth=0.46, relx=0.97, rely=0.55)

        # Configuración de los sub-frames anidados a cada uno de los sub-frames anidados a main_frame de la ventana de inicio
        upper_left_frame = tk.Frame(
            left_frame, bg="white", borderwidth=1, relief="groove")
        upper_left_frame.place(anchor="n", relheight=0.35,
                             relwidth=0.993, rely=0.002, relx=0.5)

        bottom_left_frame = tk.Frame(
            left_frame, bg="white", borderwidth=1, relief="groove")
        bottom_left_frame.place(anchor="s", relheight=0.642,
                              relwidth=0.993, rely=0.998, relx=0.5)

        upper_right_frame = tk.Frame(
            right_frame, bg="white", borderwidth=1, relief="groove")
        upper_right_frame.place(anchor="n", relheight=0.35,
                              relwidth=0.993, rely=0.002, relx=0.5)

        bottom_right_frame = tk.Frame(
            right_frame, bg="white", borderwidth=1, relief="groove")
        bottom_right_frame.place(anchor="s", relheight=0.642,
                               relwidth=0.993, rely=0.998, relx=0.5)

        # Configuración de los nodos que se ubicarán en los sub-frames anidados a cada uno de los sub-frames anidados a main_frame de la ventana de inicio

        # -------Texto de título(upper_frame)---------------------
        upper_label_text_variable = "Mis Finanzas"
        upper_text_font_style = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        title_label = tk.Label(upper_frame, text=upper_label_text_variable, fg="white",
                               bg="gray", wraplength=400, font=upper_text_font_style, width=50)
        title_label.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        # --------------------------------------------------
        # -------Imágen del título(upper_frame)---------------------
        route_image = os.path.join(current_directory + "\static", "unal.png")
        upper_image = tk.PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = tk.Label(upper_frame, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)
        # --------------------------------------------------
        # -------Texto de bienvenida(P3 - upper_left_frame)---------------------
        welcome_label_text_variable = "Bienvenidos al sistema de gestión financiera Mis Finanzas programado por: \n->Juan Pablo Mejía Gómez.\n->Leonard David Vivas Dallos.\n->José Daniel Moreno Ceballos.\n->Tomás Escobar Rivera.\n->Jorge Humberto García Botero."
        welcome_label = tk.Text(upper_left_frame, cursor="cross", fg="black", bg="white", font=(
            "Alegreya Sans", 12), wrap="word", spacing1=8, border=0)
        welcome_label.insert(tk.INSERT, welcome_label_text_variable)
        welcome_label.tag_configure("justifying", justify="center")
        welcome_label.tag_add("justifying", "1.0", tk.END)
        welcome_label.config(state="disabled")
        welcome_label.pack(expand=True, fill="both",
                           anchor="s", padx=1, pady=20)
        # --------------------------------------------------
        # -------Hoja de vida de los desarrolladores(P5 - upper_right_frame)---------------------
        # Crear el botón y asociar la función change_button_text con él
        button_developers_text = tk.StringVar(
            upper_right_frame, "1. Tomas Escobar Rivera.\n PONER HOJA DE VIDA")
        button_developers = Button(upper_right_frame, textvariable=button_developers_text, bg="white", command=change_button_text, font=(
            "Alegreya Sans", 12), activebackground="gray", activeforeground="white", border=1, relief="groove", cursor="cross", wraplength=450)
        style = font.Font(family="Times New Roman", size=12)
        button_developers.config(
            font=style, bg="#f8e5c7", border=2, relief="raised")
        button_developers.pack(expand=True, fill="both")
        # --------------------------------------------------
        # -------Fotos de los desarrolladores(P6 - bottom_right_frame)---------------------
        image_label = tk.Label(bottom_right_frame, cursor="cross")
        image_label.pack(expand=True, fill="both")
        update_image()
        # --------------------------------------------------
        # -------Imágenes asociadas al sistema(P4 - bottom_left_frame)---------------------
        # Crear un label para mostrar la imagen.
        system_image_label = tk.Label(
            bottom_left_frame, border=2, relief="groove", cursor="cross")
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
        system_image_label.bind("<Enter>", change_system_image)
        # --------------------------------------------------

        # -------Interfaz de acceso al sistema(P4 - bottom_left_frame)---------------------
        # Crear un label para inicio de sesión.
        style = font.Font(family="Times New Roman", size=13)
        login_label = tk.Label(bottom_left_frame, text="Ingresa tus datos para iniciar sesión: ",
                               fg="white", bg="black", border=1, relief="sunken", font=style)
        login_label.place(anchor="n", relheight=.1,
                          relwidth=.99, relx=0.5, rely=0.51)
        # Crear un label con el usuario ó el correo.
        user_email_label = tk.Label(bottom_left_frame, text="Usuario/Correo: ",
                                    fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_label.place(anchor="n", relheight=.20,
                               relwidth=.3, relx=0.156, rely=0.61)
        # Crear un entry para recibir el usuario ó el correo del usuario.
        user_email_entry = tk.Entry(
            bottom_left_frame, fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_entry.place(anchor="n", relheight=.20,
                               relwidth=.45, relx=0.5, rely=0.61)
        # Crear un label con el usuario ó el correo.
        password_label = tk.Label(bottom_left_frame, text="Contraseña: ",
                                  fg="white", bg="black", border=1, relief="sunken", font=style)
        password_label.place(anchor="n", relheight=.19,
                             relwidth=.3, relx=0.156, rely=0.81)
        # Crear un entry para recibir la contraseña del usuario.
        password_entry = tk.Entry(
            bottom_left_frame, fg="white", bg="black", border=1, relief="sunken", font=style)
        password_entry.place(anchor="n", relheight=.19,
                             relwidth=.45, relx=0.5, rely=0.81)
        # Crear un botón para iniciar sesión.
        login_button = tk.Button(bottom_left_frame, fg="white", bg="black", border=1, relief="sunken",
                                 font=style, text="Ingresar", activebackground="gray", activeforeground="black", cursor="cross")
        login_button.place(anchor="s", relheight=.39,
                           relwidth=.269, relx=0.860, rely=0.9999999)
        login_button.bind("<Button-1>", login)
        # Formato especial a los entry de forma dinámica
        user_email_entry.bind('<FocusIn>', format_entry_user_email)
        user_email_entry.bind('<FocusOut>', format_entry_user_email)
        password_entry.bind('<FocusIn>', format_entry_password)
        password_entry.bind('<FocusOut>', format_entry_password)

        cls.initial_window.mainloop()
    # --------------------------------------------------

    # ----------------- VENTANA PRINCIPAL --------------
    @classmethod
    def start_main_window(cls):
        # Métodos de funcionamiento de la ventana principal
        def exit_principal_window():
            cls.user = None
            cls.main_window.destroy()
            App.start_initial_window()

        def show_description():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")

        def acerca_de():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")

        def volver_main_window(frame):
            frame.destroy()
            titulo_funcionalidad.set("Bienvenido " + cls.user.getNombre() + " a Mis Finanzas")
            descripcion_funcionalidad.set("")

        # Metodos de las funcionalidades del menú
        def comprobar_suscripcion():
            def select_bank_suscription_main():
                def modify_suscription_main():
                    selected_suscription = Suscripcion.__getitem__(suscription_options_combobox.get()) 
                    try:
                        if(selected_suscription.getLimiteCuentas() < len(cls.user.getCuentasAsociadas())):
                            raise suscriptionException.underAccountsLimitException("Error. El nivel de suscripción que escogiste tiene un limite de cuentas para asociar de " + str(selected_suscription.getLimiteCuentas()) + " y el número de cuentas que tienes asociadas actualmente es de " + str(len(cls.user.getCuentasAsociadas())) + ".")
                    except suscriptionException.underAccountsLimitException:
                        messagebox.showerror("Mis finanzas", "Debes eliminar cuentas para escoger este nivel de suscripción.\nVolviendo al menú anterior.")
                        volver_main_window(suscription_frame)
                    else:
                        cls.user.setSuscripcion(selected_suscription)
                        cls.user.setLimiteCuentas(selected_suscription.getLimiteCuentas())
                        label_suscription_options.destroy()
                        suscription_options_combobox.destroy()
                        button_select.destroy()
                        button_delete.destroy()
                        label_result = tk.Label(suscription_frame, text="El nivel de suscripción del usuario " + cls.user.getNombre() + " se ha actualizado a " + cls.user.getSuscripcion().name, font=style_label)
                        label_result.pack(expand=1, fill="both", padx=2, pady=2)
                        button_result = tk.Button(suscription_frame, text="Volver al menú principal", font=style_label, command=lambda: volver_main_window(suscription_frame))
                        button_result.pack(expand=1, fill="both", padx=2, pady=2)

                selected_bank = banks_options_combobox.get()
                message = str(Banco(selected_bank).comprobarSuscripcion(cls.user))
                label_banks_options.destroy()
                banks_options_combobox.destroy()
                button_select.grid_forget()
                button_delete.grid_forget()

                confirmation = messagebox.askyesno("Mis finanzas", message + "\n¿Desea cambiar su nivel de suscripción? (Y/N):")
                if(confirmation):
                    label_suscription_options = tk.Label(master = suscription_frame, text = "Seleccione un nivel de suscripción: ", font = style_label)
                    label_suscription_options.grid(row=0, column=0, columnspan=3, padx=2, pady=2, sticky="NSEW")
                    selected_suscription = tk.StringVar(suscription_frame)
                    suscription_options_combobox = Combobox(master = suscription_frame, textvariable=selected_suscription)
                    suscription_options_combobox["values"] = [Suscripcion.getNivelesSuscripcion()[m].name for m in range(0, len(Suscripcion.getNivelesSuscripcion())) if Suscripcion.getNivelesSuscripcion()[m] != cls.user.getSuscripcion()]
                    suscription_options_combobox['state'] = 'readonly'
                    suscription_options_combobox.grid(row=1, column=0, columnspan=3, padx=2, pady=2, sticky="NSEW")
                    button_select.config(command=modify_suscription_main)
                    button_select.grid(row=0, column=3, rowspan=2, padx=2, pady=2, sticky="NSEW")
                    button_delete.grid(row=0, column=4, rowspan=2, padx=2, pady=2, sticky="NSEW")
                else:
                    volver_main_window(suscription_frame)
                    
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Modificar Suscripcion")
            descripcion_funcionalidad.set("(REVISAR)El método de instancia comprobarSuscripcion que se encuentra en la clase Banco tiene como parámetro una instancia de la clase Usuario. En este método se consulta el atributo Suscripcion de la instancia de Usuario dada por parámetro y, con base en este, se modifica el atributo de instancia limiteCuentas de tipo int de la misma instancia de Usuario. Este atributo limiteCuentas se utiliza para establecer la cantidad de instancias diferentes de la clase Cuenta que se le pueden asociar a través del método de instancia asociarCuentas, que se encuentra dentro de la clase Usuario, a la misma instancia de Usuario pasada por parámetro. Estas cuentas son añadidas al atributo de instancia cuentasAsociadas de tipo list, que se encuentra dentro de la clase Usuario. El atributo comision se invoca haciendo uso del self, luego, este valor se multiplica por K, donde K es un factor que varía con base en el atributo suscripcion del Usuario pasado por parámetro en el método.")
            style_label=font.Font(cls.main_window, family="Times New Roman", size=12)
            suscription_frame = tk.Frame(cls.subframe_main, bg="white", borderwidth=1, relief="solid")
            suscription_frame.place(relheight=0.75, relwidth=0.6, rely=0.25, relx=0.2)
            try:
                asociated_banks_user = cls.user.mostrarBancosAsociados()
            except banksException.NoBanksException:
                messagebox.showerror("Mis finanzas", "No existen bancos asociados a este usuario. Inténtelo de nuevo más tarde.")
                volver_main_window(suscription_frame)

            else:
                label_banks_options = tk.Label(master = suscription_frame, text = "Seleccione un banco de la lista de bancos asociados al usuario {}:".format(cls.user.getNombre()), font = style_label)
                label_banks_options.grid(row=0, column=0, columnspan=3, padx=2, pady=2, sticky="NSEW")
                selected_bank = tk.StringVar(suscription_frame)
                banks_options_combobox = Combobox(master = suscription_frame, textvariable=selected_bank)
                banks_options_combobox["values"] = [asociated_banks_user[m].getNombre() for m in range(0, len(asociated_banks_user))]
                banks_options_combobox['state'] = 'readonly'
                banks_options_combobox.grid(row=1, column=0, columnspan=3, padx=2, pady=2, sticky="NSEW")
                button_select = tk.Button(master=suscription_frame, text="Aceptar", command=select_bank_suscription_main)
                button_select.grid(row=0, column=3, rowspan=2, padx=2, pady=2, sticky="NSEW")
                button_delete = tk.Button(master=suscription_frame, text="Borrar")
                button_delete.grid(row=0, column=4, rowspan=2, padx=2, pady=2, sticky="NSEW")

            #suscripcion_forms = FieldFrame(tituloCriterios = "Prueba Criterio", criterios = ["prueba 1", "prueba 2", "prueba 3"], tituloValores = "Prueba Valor", valores = [1, 2])

        def invertir_saldo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Invertir Saldo")
            descripcion_funcionalidad.set("(REVISAR)El método de instancia invertirSaldo que se encuentra en la clase Ahorros consulta el atributo de instancia titular de tipo Usuario, de la instancia de Ahorros utilizada para ejecutar el método, usando el operador self y el método de instancia getTitular, posteriormente, verifica el atributo de instancia suscripcion de la instancia titular y obtiene la constante probabilidad_Inversion de tipo float asociada a este. Esta última constante se utiliza para realizar un cálculo aritmético que se almacena dentro de una variable de tipo double llamada rand y se evalúa que rand sea mayor ó igual a uno. Posteriormente, si la condición es true: se realiza un Movimiento ó si la condición es false: retorna un String.")

        def consignar_saldo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Consignar Saldo")
            descripcion_funcionalidad.set("(REVISAR)El método estático crearMovimiento que se encuentra en la clase Movimientos recibe como parámetros una instancia de Ahorros, un enum de Categoria, un dato de tipo double llamado saldo_consignar y un objeto de tipo date. Este método consulta el atributo de clase cuentasTotales de tipo list de la clase Cuenta, posteriormente se crea una instancia de la clase Movimientos que se asocia a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario, finalmente, se retorna la instancia de Movimientos.")

        def transferir_saldo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Transferir Saldo")
            descripcion_funcionalidad.set("(REVISAR)El método estático modificarSaldo que se encuentra en la clase Movimientos recibe como parámetros una instancia de Usuario, dos instancias de Ahorros, un enum de Categoria y un dato de tipo double llamado cantidad. Este método consulta el atributo de instancia cuentasAsociadas de tipo list de la instancia de Usuario pasada por parámetro, posteriormente comprueba que el atributo de instancia llamado origen de tipo Ahorros pasado por parámetro se encuentre dentro de la lista cuentasAsociadas. Posteriormente se llama al método crearMovimiento de la clase Movimientos y éste último es asociado a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario, finalmente, se retorna la instancia de Movimientos.")

        def compra_corriente():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Comprar con cuenta corriente")
            descripcion_funcionalidad.set("Agregar la descripcion en el metodo compra_corriente y agregar aca el funcionamiento de su funcionalidad")

        def asesoramiento_inversiones():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Asesoramiento de Inversiones")
            descripcion_funcionalidad.set("Agregar la descripcion en el metodo asesoramiento_inversiones y agregar aca el funcionamiento de su funcionalidad")
            interfaz_asesoramiento_inversiones()

        def compra_cartera(cuenta = None):
            print(Corriente.__name__)
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Compra Catera")
            label_description.config(
                text="Agregar la descripcion en el metodo compra_cartera y agregar aca el funcionamiento de su funcionalidad")
            #Desarrollo de la funcionalidad

            if cuenta == None:

                #Cambio prueba, a espera confirmación de la SERIALIZACIÓN
                cls.user.getCuentasCorrienteAsociadas()[0].setDisponible(500000)

                #Arreglo que almacena las cuentas con deuda alguna
                cuentasEnDeuda = cls.user.retornarDeudas()

                #Arreglo que almacena las cuentas asociadas a un usuario
                cuentasAux = cls.user.getCuentasAsociadas()

                cuentasAux1 = cls.user.getCuentasCorrienteAsociadas()

                for cuentas in cuentasAux1:
                    print(cuentas)

                #Comprobación de existencia de Cuentas Corriente por parte del Usuario
                if len(cuentasAux1) <= 1:
                    messagebox.showerror("Error", "El usuario " + cls.user.getNombre() + " no alcanza la cantidad de cuentas Corriente necesarias para desarrollar la funcionalidad, recuerda que para ejecutar una compra de cartera necesitas por lo menos dos cuentas Corriente, una de ellas con una Deuda.")
                    return
                
                if len(cuentasEnDeuda) == 0:
                    messagebox.showerror("Error", "El usuario " + cls.user.getNombre() + " no tiene préstamos asociados, no es posible realizar la funcionalidad.")
                    return
                
                framecc = tk.Frame(cls.subframe_main)
                framecc.pack(expand=True, fill="both")

                confirmacion_Compra = False
                cuenta_Compra = 0

                def eleccion(evento):
                    global confirmacion_Compra
                    eleccion = eleccion_cuenta.get()
                    if eleccion == "Seleccionar Cuenta":
                        messagebox.showerror("Mala elección", "Por favor, selecciona una cuenta válida")
                    else:
                        message = "Información de la cuenta: \n" + str(cuentasEnDeuda[int(eleccion[0])-1]) + "\nConfirme por favor si es la cuenta deseada"
                        confirmacion_Compra = messagebox.askyesno("Confirmación", message= message)
                        if confirmacion_Compra:
                            global cuenta_Compra
                            cuenta_Compra = int(eleccion[0])

                impresion_1 = "Cuentas a nombre de " + cls.user.getNombre() + " con préstamos asociados: "
                label_impresion = tk.Label(framecc, text=impresion_1)
                label_impresion.grid(row=0, column=0, columnspan=10)

                Tablas.impresionCuentasCorriente(cuentasEnDeuda, framecc, 1)

                cuen_comb = ["Seleccionar Cuenta"]
                    
                i = 1
                for cuenta_deuda in cuentasEnDeuda:
                    cadena = str(i) + ". ID:" + str(cuenta_deuda.getId()) + " " + cuenta_deuda.getNombre()
                    cuen_comb.append(cadena)
                    i += 1

                eleccion_cuenta = Combobox(framecc, values= cuen_comb)
                eleccion_cuenta.set("Seleccionar Cuenta")
                eleccion_cuenta.bind("<<ComboboxSelected>>", eleccion)
                eleccion_cuenta.grid(row = len(cuentasEnDeuda) + 2, column= 20, padx=10)

                while not confirmacion_Compra:
                    cls.subframe_main.update()
                
                cuentasAux.remove(cuentasEnDeuda[cuenta_Compra - 1])

                #Arreglo que almacena las cuentas capaces de recibir la deuda
                cuentas_capaces_deuda = cls.user.capacidad_endeudamiento(cuentasAux, cuentasEnDeuda[cuenta_Compra-1])

                #Arreglo que almacena las tasas de interes aplicables con orden del arreglo anterior
                tasacion_cuentas = Banco.verificar_tasas_de_interes(cls.user, cuentas_capaces_deuda)

                cuentasAux.append(cuentasEnDeuda[cuenta_Compra-1])

                if len(cuentas_capaces_deuda) == 0:
                    messagebox.showerror("Error", "Ninguna de las cuentas Corriente que posees tiene la capacidad de recibir la deuda de la cuenta escogida.")
                    return
                
                for widget in framecc.winfo_children():
                    widget.destroy()

                confirmacion_Destino = False
                cuenta_Destino = 0

                def eleccion_2(evento):
                    global confirmacion_Destino
                    eleccion = eleccion_cuenta.get()
                    if eleccion == "Seleccionar Cuenta":
                        messagebox.showerror("Mala elección", "Por favor, selecciona una cuenta válida")
                    else:
                        message = "Información de la cuenta: \n" + str(cuentas_capaces_deuda[int(eleccion[0])-1]) + "\nConfirme por favor si es la cuenta deseada"
                        confirmacion_Destino = messagebox.askyesno("Confirmación", message= message)
                        if confirmacion_Destino:
                            global cuenta_Destino
                            cuenta_Destino = int(eleccion[0])

                impresion_2 = "Las cuentas a su nombre que pueden recibir la deuda de la Cuenta escogida son: "
                label_impresion_2 = tk.Label(framecc, text=impresion_2)
                label_impresion_2.grid(row=0, column=0, columnspan=11)

                Tablas.impresionCuentasCorrienteInteres(cuentas_capaces_deuda, tasacion_cuentas, framecc, 1)

                cuen_comb_2 = ["Seleccionar Cuenta"]
                    
                j = 1
                for cuenta_capaz in cuentas_capaces_deuda:
                    cadena = str(i) + ". ID:" + str(cuenta_capaz.getId()) + " " + cuenta_capaz.getNombre()
                    cuen_comb_2.append(cadena)
                    j += 1

                eleccion_cuenta_compra = Combobox(framecc, values= cuen_comb_2)
                eleccion_cuenta_compra.set("Seleccionar Cuenta")
                eleccion_cuenta_compra.bind("<<ComboboxSelected>>", eleccion_2)
                eleccion_cuenta_compra.grid(row = len(cuentas_capaces_deuda) + 2, column= 20, padx=10)

                while not confirmacion_Destino:
                    cls.subframe_main.update()

                deuda = Cuenta.dineroATenerDisponible(cuentas_capaces_deuda[cuenta_Destino - 1], cuentasEnDeuda[cuenta_Compra - 1].getDivisa())

                #Atributo auxiliar para almacenar decision de periodicidad
                eleccion_periodicidad = Cuotas.C1

                message_per = "¿Desea mantener la periodicidad del pago de la deuda?"
                validacion_periodicidad = messagebox.askyesno("Elección", message= message_per)
                if validacion_periodicidad:
                    message_info_per = "Perfecto, la deuda mantendrá un plazo de pago a " + cuentas_capaces_deuda[cuenta_Destino - 1].getPlazo_Pago() + "."
                    messagebox.showinfo("Anuncio", message_info_per)
                    eleccion_periodicidad = cuentas_capaces_deuda[cuenta_Destino - 1].getPlazo_Pago()
                else:
                    #Atributo de validacion de la seleccion de periodicidad
                    for widget in framecc.winfo_children():
                        widget.destroy()
                    
                    confirmacion_Periodicidad = False
                    def eleccion_3(evento):
                        global confirmacion_Periodicidad
                        eleccion = eleccion_cuenta.get()
                        if eleccion == "Seleccionar Cuotas":
                            messagebox.showerror("Mala elección", "Por favor, selecciona una cantidad de cuotas válida")
                        else:
                            eleccion_aux = eleccion.split()
                            message = "Cantidad de cuotas escogida: " + eleccion_aux[0]
                            confirmacion_Periodicidad = messagebox.askyesno("Confirmación", message= message)
                            if confirmacion_Periodicidad:
                                eleccion_asignar = int(eleccion_aux[0])
                                global eleccion_periodicidad
                                if eleccion_asignar == 1:
                                    eleccion_periodicidad = Cuotas.C1
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C1 + ".")
                                elif eleccion_asignar == 6:
                                    eleccion_periodicidad = Cuotas.C6
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C6 + ".")
                                elif eleccion_asignar == 12:
                                    eleccion_periodicidad = Cuotas.C12
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C12 + ".")
                                elif eleccion_asignar == 18:
                                    eleccion_periodicidad = Cuotas.C18
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C18 + ".")
                                elif eleccion_asignar == 24:
                                    eleccion_periodicidad = Cuotas.C24
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C24 + ".")
                                elif eleccion_asignar == 36:
                                    eleccion_periodicidad = Cuotas.C36
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C36 + ".")
                                else:
                                    eleccion_periodicidad = Cuotas.C48
                                    messagebox.showinfo("Información", "Deuda establecida a: " + Cuotas.C48 + ".")

                    impresion_3 = "Por favor seleccione la nueva periodicidad de la Deuda: "
                    label_impresion_3 = tk.Label(framecc, text=impresion_3)
                    label_impresion_3.grid(row=0, column=0, columnspan=5)

                    cuen_comb_3 = ["Seleccionar Cuotas", "1 Cuota", "6 Cuotas", "12 Cuotas", "18 Cuotas", "24 Cuotas", "36 Cuotas", "48 Cuotas"]

                    eleccion_cuenta_compra = Combobox(framecc, values= cuen_comb_3)
                    eleccion_cuenta_compra.set("Seleccionar Cuotas")
                    eleccion_cuenta_compra.bind("<<ComboboxSelected>>", eleccion_3)
                    eleccion_cuenta_compra.grid(row = 3, column= 2)

                    while not confirmacion_Periodicidad:
                        cls.subframe_main.update()
                
                vistaPrevia = Corriente.vistaPreviaMovimiento(cuentas_capaces_deuda[cuenta_Destino - 1], eleccion_periodicidad, deuda, tasacion_cuentas[cuenta_Destino - 1])

                #Lo mismo de arriba para pago de primer mes



                #password_entry.delete(0, tk.END)
                #user_email_entry.delete(0, tk.END)
                print("hola")
            else:
                pass

        def calculadora_financiera():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Calculadora Financiera")
            label_description.config(
                text="Agregar la descripcion en el metodo calculadora_financiera y agregar aca el funcionamiento de su funcionalidad")

        def pedir_prestamo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Pedir Prestamo")
            label_description.config(
                text="¿Necesitas dinero? realiza un prestamo con tu banco.\nPara pedir un prestamo es necesario que cuentes con una cuenta de ahorros, la cantidad de dinero que puedes prestar va a depender de tu nivel de suscripción y del banco asociado a tu cuenta")

        def pagar_prestamo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Pagar Prestamo")
            descripcion_funcionalidad.set("Es importante pagar tus deudas para poder confiando en ti.\n En esta sección puedes pagar tus prestamos, si no has realizado ningun prestamos y quieres hacerlo ingresa a la sección Pedir Prestamo")

        def interfaz_asesoramiento_inversiones():
            # FALRA IMPLEMENTAR TODA LA LOGICA
            tolerancia_riesgos = ""
            monto_inversion = ""

            class Metas:
                @staticmethod
                def revisionMetas():
                    pass
                
                @staticmethod
                def determinar_plazo():
                    return "Medio"


            class Movimientos:
                nombre_categoria = "Comida"
                cantidad_categoria = 500


            def mostrar_siguiente():
                global tolerancia_riesgos, monto_inversion

                # Obtener los valores seleccionados o ingresados
                tolerancia_riesgos = texto1.get()
                monto_inversion = texto2.get()

                # Ocultar el botón "Siguiente"
                boton_siguiente.pack_forget()

                # Eliminar todos los widgets del contenedor
                for widget in frame.winfo_children():
                    widget.destroy()

                # Obtener los datos de la revisión de metas
                resultado = Metas.revisionMetas()
                # nombre = resultado.getNombre()
                # cantidad = resultado.getCantidad()
                # fecha = resultado.getFechaNormal()
                nombre = "pepito"
                cantidad = 100
                fecha_normal = "10/10/2025"
                # Mostrar el resultado por pantalla
                resultado_texto = f"Tienes una meta para una fecha muy próxima: {nombre}, {cantidad}, {fecha_normal}"
                label_siguiente = tk.Label(frame, text=resultado_texto, font=font.Font(family="Times New Roman", size=12))
                label_siguiente.pack()

                # Agregar mensaje de confirmación para cambiar la fecha
                mensaje_confirmacion = "¿Desea cambiar la fecha de la meta?"
                label_confirmacion = tk.Label(frame, text=mensaje_confirmacion, font=font.Font(family="Times New Roman", size=12))
                label_confirmacion.pack()

                # Crear botones "Sí" y "No" centrados
                botones_frame = tk.Frame(frame)
                botones_frame.pack()

                boton_si = tk.Button(botones_frame, text="Sí", command=mostrar_campo_fecha, font=font.Font(family="Times New Roman", size=12))
                boton_si.pack(side=tk.LEFT, padx=10)

                boton_no = tk.Button(botones_frame, text="No", command=no_cambiar_fecha_meta, font=font.Font(family="Times New Roman", size=12))
                boton_no.pack(side=tk.LEFT, padx=10)


            def mostrar_campo_fecha():
                global entry_fecha

                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje y campo de texto para la fecha
                mensaje_fecha = "Ingrese la nueva fecha de la meta:"
                label_fecha = tk.Label(frame, text=mensaje_fecha, font=font.Font(family="Times New Roman", size=12))
                label_fecha.pack()

                entry_fecha = tk.Entry(frame, font=font.Font(family="Times New Roman", size=12))
                entry_fecha.pack()

                # Crear botón "Guardar"
                boton_guardar = tk.Button(frame, text="Guardar", command=guardar_fecha_meta, font=font.Font(family="Times New Roman", size=12))
                boton_guardar.pack()


            def guardar_fecha_meta():
                nueva_fecha = entry_fecha.get()
                plazo_inversion = Metas.determinar_plazo()

                mensaje_confirmacion = f"La fecha de la meta ha sido cambiada a: {nueva_fecha}\n"
                mensaje_confirmacion += f"Plazo de inversión: {plazo_inversion}"

                # Mostrar mensaje de confirmación
                messagebox.showinfo("Mensaje", mensaje_confirmacion)
                mostrar_advertencia()


            def no_cambiar_fecha_meta():
                plazo_inversion = Metas.determinar_plazo()

                mensaje_confirmacion = f"La fecha de la meta no ha sido cambiada.\n"
                mensaje_confirmacion += f"Plazo de inversión: {plazo_inversion}"

                # Mostrar mensaje de confirmación
                messagebox.showinfo("Mensaje", mensaje_confirmacion)
                mostrar_advertencia()


            def mostrar_advertencia():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                mensaje_advertencia = "Advertencia: Con el fin de hacer un buen asesoramiento analizaremos sus movimientos para encontrar la categoría en la que más dinero ha gastado."
                label_advertencia = tk.Label(frame, text=mensaje_advertencia, font=font.Font(family="Times New Roman", size=12))
                label_advertencia.pack()

                # Mostrar la categoría en la que más dinero se ha gastado
                mensaje_categoria = "La categoría en la que más dinero ha gastado es: " + Movimientos.nombre_categoria
                mensaje_categoria += " que suma un total de " + str(Movimientos.cantidad_categoria)
                label_categoria = tk.Label(frame, text=mensaje_categoria, font=font.Font(family="Times New Roman", size=12))
                label_categoria.pack()

                # Crear botones "Sí" y "No"
                botones_frame = tk.Frame(frame)
                botones_frame.pack()

                boton_si = tk.Button(botones_frame, text="Sí", command=crear_meta_ahorro, font=font.Font(family="Times New Roman", size=12))
                boton_si.pack(side=tk.LEFT, padx=10)

                boton_no = tk.Button(botones_frame, text="No", command=no_crear_meta_ahorro, font=font.Font(family="Times New Roman", size=12))
                boton_no.pack(side=tk.LEFT, padx=10)

            def crear_meta_ahorro():
                messagebox.showinfo("Mensaje", "Usaremos tus datos para crear la meta. Luego vamos a priorizar esa meta respecto a las demás que tengas")
                messagebox.showinfo("Mensaje", "La meta ha sido creada y puesta como prioridad en tu lista de metas")
                # LLAMAR A VER_METAS
                mostrar_recomendaciones()

            def no_crear_meta_ahorro():
                mostrar_recomendaciones()

            def mostrar_recomendaciones():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de recomendaciones
                mensaje_recomendaciones = "En base a los datos recolectados, deberías invertir tu dinero en estos sectores:"
                recomendaciones = "- Servicios de comunicación\n- Consumo discrecional\n- Bienes raíces"

                label_recomendaciones = tk.Label(frame, text=mensaje_recomendaciones, font=font.Font(family="Times New Roman", size=12))
                label_recomendaciones.pack()

                label_sectores = tk.Label(frame, text=recomendaciones, font=font.Font(family="Times New Roman", size=12))
                label_sectores.pack()

                # Mostrar mensaje adicional
                mensaje_adicional = "Nota: Hay un banco asociado al portafolio: Banco de Colombia, con una tasa de interés del 5%"
                label_adicional = tk.Label(frame, text=mensaje_adicional, font=font.Font(family="Times New Roman", size=12))
                label_adicional.pack()

                # Mostrar botón "Siguiente"
                boton_siguiente = tk.Button(frame, text="Siguiente", command=mostrar_prestamo, font=font.Font(family="Times New Roman", size=12))
                boton_siguiente.pack()


            def mostrar_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de préstamo
                mensaje_prestamo = "Finalmente, para mejorar aún más tu inversión te recomendamos hacer un préstamo. ¿Deseas hacer el préstamo?"
                label_prestamo = tk.Label(frame, text=mensaje_prestamo, font=font.Font(family="Times New Roman", size=12))
                label_prestamo.pack()

                # Crear botones "Sí" y "No" centrados
                botones_frame = tk.Frame(frame)
                botones_frame.pack()

                boton_si = tk.Button(botones_frame, text="Sí", command=hacer_prestamo, font=font.Font(family="Times New Roman", size=12))
                boton_si.pack(side=tk.LEFT, padx=10)

                boton_no = tk.Button(botones_frame, text="No", command=no_hacer_prestamo, font=font.Font(family="Times New Roman", size=12))
                boton_no.pack(side=tk.LEFT, padx=10)

            def hacer_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de solicitud de préstamo
                mensaje_prestamo = "Las tasas de interés de los préstamos están muy altas, pero tenemos la solución perfecta para ti, aunque no sea la más correcta... Vas a hacer un préstamo con el usuario gota a gota."
                label_prestamo = tk.Label(frame, text=mensaje_prestamo, font=font.Font(family="Times New Roman", size=12), wraplength=ventana.winfo_width())
                label_prestamo.pack()

                # Mostrar campo de texto para ingresar el monto del préstamo
                label_monto = tk.Label(frame, text="Ingrese el monto que desea solicitar prestado:", font=font.Font(family="Times New Roman", size=12))
                label_monto.pack()

                texto_monto = tk.StringVar()
                entry_monto = tk.Entry(frame, textvariable=texto_monto, font=font.Font(family="Times New Roman", size=12))
                entry_monto.pack()

                # Crear botón "Guardar"
                boton_guardar = tk.Button(frame, text="Guardar", command=guardar_monto_prestamo, font=font.Font(family="Times New Roman", size=12))
                boton_guardar.pack()

            def guardar_monto_prestamo():
                messagebox.showinfo("Mensaje", "Era una trampa, ahora el usuario gota a gota vació tu cuenta")
                no_hacer_prestamo()

            def no_hacer_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de despedida
                mensaje_despedida = "Ha sido un placer asesorarte en este proceso, espero que nuestra recomendación haya sido de ayuda."
                label_despedida = tk.Label(frame, text=mensaje_despedida, font=font.Font(family="Times New Roman", size=12))
                label_despedida.pack()

                boton_cerrar = tk.Button(ventana, text="Cerrar", command=ventana.destroy)
                boton_cerrar.pack()

                ventana.mainloop()


            # Crear ventana principal
            ventana = tk.Tk()
            ventana.title("Aplicación de Campos de Texto")

            # Obtener el tamaño de la pantalla
            ancho_pantalla = ventana.winfo_screenwidth()
            alto_pantalla = ventana.winfo_screenheight()

            # Establecer el tamaño de la ventana
            ventana.geometry(f"{int(ancho_pantalla * 0.6)}x{int(alto_pantalla * 0.6)}")

            # Crear contenedor de campos de texto
            frame = tk.Frame(ventana, bd=2, relief=tk.GROOVE, padx=10, pady=10)
            frame.pack(fill=tk.BOTH, expand=True)

            # Crear etiqueta y menú desplegable
            label1 = tk.Label(frame, text="Tolerancia a Riesgos:", font=font.Font(family="Times New Roman", size=12))
            label1.pack()

            texto1 = tk.StringVar()
            combobox = Combobox(frame, textvariable=texto1, font=font.Font(family="Times New Roman", size=12))
            combobox['values'] = ('', 'Baja', 'Media', 'Alta')
            combobox.current(0)
            combobox.pack()

            # Crear etiqueta y campo de texto
            label2 = tk.Label(frame, text="¿Cuánto dinero deseas invertir?:", font=font.Font(family="Times New Roman", size=12))
            label2.pack()

            texto2 = tk.StringVar()
            entry2 = tk.Entry(frame, textvariable=texto2, font=font.Font(family="Times New Roman", size=12))
            entry2.pack()

            # Crear botón "Siguiente"
            boton_siguiente = tk.Button(ventana, text="Siguiente", command=mostrar_siguiente, font=font.Font(family="Times New Roman", size=12))
            boton_siguiente.pack()

            # Configurar el comportamiento de ajuste al cambio de tamaño
            ventana.pack_propagate(False)

            ventana.mainloop()

        # Configuración básica de parámetros de la ventana Principal
        cls.main_window = tk.Tk()
        cls.main_window.geometry("1390x800")
        cls.main_window.title("Mis Finanzas")
        #cls.main_window.resizable(0, 0)
        current_directory = os.path.dirname(os.path.abspath(__file__))
        route_logo = os.path.join(current_directory + "\static", "logo.png")
        logo = tk.PhotoImage(file=route_logo)
        cls.main_window.iconphoto(True, logo)

        # Configuración básica de los parámetros del main_frame en la ventana Principal
        main_frame = tk.Frame(cls.main_window, bg="#DFDEDE")
        main_frame.pack(fill="both", expand=True)
        titulo_funcionalidad = tk.StringVar(main_frame, value="Bienvenido " + cls.user.getNombre() + " a Mis Finanzas") 

        # Configuración de los subs-frames

        # subframe del titulo
        subframe_title = tk.Frame(main_frame, bg="black",
                             borderwidth=1, relief="solid")
        subframe_title.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)
        cls.subframe_main = tk.Frame(
            main_frame, bg="white", borderwidth=1, relief="solid")
        cls.subframe_main.place(
            relheight=0.85, relwidth=0.94, rely=0.15, relx=0.03)

        # Opciones dentro del menú procesos y consultas
        proceso1 = "Modificar suscripcion"
        proceso2 = "Invertir saldo de mi cuenta"
        proceso3 = "Consignar saldo a mi cuenta"
        proceso4 = "Transferir saldo entre cuentas"
        proceso5 = "Compra con cuenta corriente"
        proceso6 = "Gestionar prestamos"
        proceso7 = "Asesoramiento de inversiones"
        proceso8 = "Compra de cartera"
        proceso9 = "Calculadora financiera"

        # Configuración de barra de menú para ventana principal
        home_menu = tk.Menu(subframe_title, cursor="cross")
        
        # Configuración del menú archivo
        archivo = tk.Menu(home_menu, tearoff=0)
        archivo.add_command(label="Aplicación",
                            activebackground="gray", activeforeground="white", command=show_description)
        archivo.add_command(label="Cerrar sesión", command=exit_principal_window,
                            activebackground="gray", activeforeground="white")

        # Configuración del menú de ayuda
        ayuda_menu = tk.Menu(home_menu, tearoff=0)
        ayuda_menu.add_command(label="Acerca de", command=acerca_de,
                             activebackground="gray", activeforeground="white")

        # Configuración del menú de Procesos y Consultas
        procesos_consultas = tk.Menu(home_menu, tearoff=0)

        # Agregamos los submenús al gestionar prestamos.
        prestamos_menu = tk.Menu(procesos_consultas, tearoff=0)
        prestamos_menu.add_command(label="Pedir Prestamos", command=pedir_prestamo,
                                   activebackground="gray", activeforeground="white")
        prestamos_menu.add_command(label="Pagar Prestamos", command=pagar_prestamo,
                                   activebackground="gray", activeforeground="white")

        # Agregamos los submenús a la barra de menú.
        procesos_consultas.add_command(label=proceso1, command=comprobar_suscripcion,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso2, command=invertir_saldo,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso3, command=consignar_saldo,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso4, command=transferir_saldo,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso5, command=compra_corriente,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_cascade(label=proceso6, menu=prestamos_menu,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso7, command=asesoramiento_inversiones,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso8, command=compra_cartera,
                             activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label=proceso9, command=calculadora_financiera,
                             activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Archivo", menu=archivo,
                              activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Procesos y Consultas", menu=procesos_consultas,
                              activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Ayuda", menu=ayuda_menu,
                              activebackground="gray", activeforeground="white")

        #Añadir la barra de menú a la ventana principal
        cls.main_window.config(menu=home_menu)

        # -------Texto de título(subframe_title)---------------------
        title_Font_Style = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        label_title = tk.Label(subframe_title, textvariable=titulo_funcionalidad, fg="white",
                               bg="gray", wraplength=400, font=title_Font_Style, width=50)
        label_title.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        
        # -------Imagen del titulo(subframe_title)
        route_image = os.path.join(current_directory + "\static", "unal.png")
        upper_image = tk.PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = tk.Label(subframe_title, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)

        # ------------Descripcion de la funcionalidad
        subframe_description = tk.Frame(
            cls.subframe_main, bg="gray", borderwidth=1, relief="solid")
        subframe_description.place(
            relheight=0.25, relwidth=1, rely=0.0, relx=0.0)
        descripcion_font_style = font.Font(size=12, family="Alegreya Sans")
        descripcion_funcionalidad = tk.StringVar(main_frame, value="Ad cillum enim occaecat aliqua ad ad sit. Reprehenderit laboris elit veniam minim esse elit. Anim deserunt officia irure proident non velit duis sint quis aute Lorem id.")
        label_description = tk.Label(subframe_description, textvariable=descripcion_funcionalidad,
                                    fg="white", bg="gray", font=descripcion_font_style, wraplength=800)
        label_description.pack(fill="both", expand=True)

        cls.main_window.mainloop()
    # --------------------------------------------------

    @classmethod
    def getMainWindow(cls):
        return cls.main_window
    
    @classmethod
    def getInitialWindow(cls):
        return cls.initial_window
    
    @classmethod
    def getSubframeMain(cls):
        return cls.subframe_main
    
    @classmethod
    def getConf(cls):
        return cls.conf
    
    @classmethod
    def setConf(cls, conf):
        cls.conf = conf
# --------------------------------------------------

if __name__ == "__main__":
    # Poner código para ejecutar la interfaz
    App().start_initial_window()
