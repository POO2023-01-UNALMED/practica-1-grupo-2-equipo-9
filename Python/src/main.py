from tkinter import font, messagebox, Button, Frame, Label, Entry, Text, StringVar, END, PhotoImage, Tk, Menu, INSERT, CENTER, LEFT
from tkinter.ttk import Combobox
import os
from excepciones import banksException, suscriptionException, usersException, accountsException, genericException
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
from gestorAplicación.interno.categoria import Categoria
from gestorAplicación.interno.categoria import Categoria
from gestorAplicación.interno.deuda import Deuda
from gestorAplicación.externo.cuotas import Cuotas
from gestorAplicación.externo.tablas import Tablas
from datetime import datetime

# FAVOR SER ORDENADOS CON EL CÓDIGO Y COMENTAR TODO BIEN. USAR SNAKECASE. NOMBRAR VARIABLES Y MÉTODOS EN INGLÉS. CODIFICAR EXCEPCIONES EN EL PAQUETE EXCEPCIONES

# ------ FIELD FRAME PARA DIÁLOGOS DE TEXTO --------
class FieldFrame(Frame):
    def __init__(self, tituloCriterios, criterios, tituloValores, frame,**kwargs):
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
        self.field_frame = Frame(frame[0], bg="white", borderwidth=1, relief="solid")
        if frame[0].winfo_name() == "subframe_main":
            self.field_frame.place(relheight=0.75, relwidth=0.6, rely=0.25, relx=0.2)
        else:
            self.field_frame.grid(row=frame[1], column=frame[2], columnspan=frame[3], rowspan=frame[4], padx=2, pady=2, sticky="NSEW")
            frame[0].columnconfigure(0, weight=1)

        title_style = font.Font(family="Times New Roman", size=13, weight="bold")
        criteria_style = font.Font(family="Times New Roman", size=13, underline=1)
        entry_style = font.Font(family="Times New Roman", size=13)

        title_criteria = Label(master=self.field_frame, textvariable = self.tituloCriterios, width=35, bg="white", fg="black", font=title_style, border=1, relief="ridge")
        title_criteria.grid(row=0, column=0, padx=3, pady=3)
        title_value = Label(master=self.field_frame, textvariable = self.tituloValores, width=35, bg="white", fg="black", font=title_style, border=1, relief="ridge")
        title_value.grid(row=0, column=1, padx=3, pady=3)

        for i in range(0, len(self.getCriterios())):
            entry = Entry(master=self.field_frame, width=35, bg="white", fg="black", font=entry_style, border=1, relief="groove", name=str(self.getCriterios()[i]).lower().replace(" ", ""))
            label = Label(master=self.field_frame, text = str(self.getCriterios()[i]), width=35, bg="white", fg="black", font=criteria_style, border=1, relief="groove")
    
            if(self.getValores() != None):
                try:
                    entry.insert(0, self.getValores()[i])
                except:
                    pass
            if(self.getCriterios()[i] in self.getHabilitado()):
                entry.config(state="disabled")

            entry.grid(column=1, row=i + 1, padx=3, pady=3)
            label.grid(column=0, row=i + 1, padx=3, pady=3)
        self.field_frame.columnconfigure(1, weight=1)
        self.field_frame.columnconfigure(0, weight=1)

    #Gets & Sets
    def setValue(self, criterio, value):
        self.criterios[criterio] = value
    def getValue(self, criterio):
        return self.criterios[criterio]
    def setTituloCriterios(self, tituloCriterios):
        self.tituloCriterios = StringVar()
        self.tituloCriterios.set(tituloCriterios)
    def getTituloCriterios(self):
        return self.tituloCriterios
    def setCriterios(self, criterios):
        self.criterios = criterios
    def getCriterios(self):
        return self.criterios
    def setTituloValores(self, tituloValores):
        self.tituloValores = StringVar()
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
    def getValoresInsertados(self):
        l = []
        for m in self.field_frame.winfo_children():
            if type(m).__name__ == "Entry":
                l.append(m)
        return (l)
    def getFieldFrameObject(self):
        return self.field_frame
# --------------------------------------------------

# ----------------- APP ----------------
class App():
    # Guardar objetos al sistema (LOS OBJETOS NO SE SOBREESCRIBEN, TODOS LOS OBJETOS DE LA MISMA CLASE QUE SE VAYAN A SERIALIZAR DEBEN SER INCLUIDOS EN UNA SOLA LISTA CUANDO SE LLAMA AL MÉTODO SERIALIZAR. LAS LLAMADAS A SERIALIZAR SIEMPRE DEBEN SER LO ÚLTIMO)       
    

    # Variables de clase para funcionamiento de la app
    initial_window = None
    main_window = None
    user = None
    subframe_main = None
    image_index = 0  # Variable para realizar un seguimiento del índice del pack de imagenes de los desarrolladores
    current_directory = os.path.dirname(os.path.abspath(__file__))

    # ----------------- VENTANA INICIAL ----------------
    @classmethod
    def start_initial_window(cls):
        
        # Métodos de funcionamiento de la ventana de inicio
        def exit_initial_window():
            # Guardar objetos del sistema
            Serializador.serializar("Usuarios")
            Serializador.serializar("Cuentas")
            Serializador.serializar("Estados")
            Serializador.serializar("Bancos")
            Serializador.serializar("Metas")
            Serializador.serializar("Movimientos")

            cls.initial_window.destroy()

        def show_description():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")

        def login(event):
            name_email_user = str(user_email_entry.get())
            password_user = str(password_entry.get())
            try:
                possible_user = Usuario.verificarCredenciales(name_email_user, password_user)
            except usersException.NoUserFoundException:
                confirmation = messagebox.askretrycancel("Mis finanzas", usersException.NoUserFoundException.show_message())
                if confirmation:
                    user_email_entry.delete(0, END)
                    password_entry.delete(0, END)
                else:
                    exit_initial_window()
            else:
                cls.user = possible_user
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
            #CAMBIAR LA VARIABLE STYLE PARA DARLE UN ESTILO PARTICULAR A CADA UNO
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
                    "4. Jorge Humberto Gaviria Botero.\n Estudiante de Ingeniería de Sistemas e Informática con pasión por la programación y el desarrollo de software. Me motiva el deseo constante de mejorar mis habilidades y conocimientos en este campo en constante evolución. A lo largo de mi trayectoria, he participado en diversos cursos y proyectos de software, que han contribuido significativamente a mi crecimiento profesional. ")
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
                    "1. Tomas Escobar Rivera.\n Soy un apasionado programador con experiencia en múltiples lenguajes de programación. Soy una persona comprometida, organizada y con habilidades de trabajo en equipo. Me adapto rápidamente a nuevos entornos y disfruto de los desafíos que suponen resolver problemas complejos mediante el uso de la programación.")
                style = font.Font(family="Times New Roman", size=12)
                button_developers.config(
                    font=style, bg="#f8e5c7", border=2, relief="raised")
            update_image()

        def update_image():
            # Añadir las rutas para cada imágen
            pablo_1 = os.path.join(cls.current_directory +
                                   "\static\pablo_photos", "1.png")
            pablo_2 = os.path.join(cls.current_directory +
                                   "\static\pablo_photos", "2.png")
            pablo_3 = os.path.join(cls.current_directory +
                                   "\static\pablo_photos", "3.png")
            pablo_4 = os.path.join(cls.current_directory +
                                   "\static\pablo_photos", "4.png")
            
            tomas_1 = os.path.join(cls.current_directory +
                                   "\static\\tomas_photos", "1.png")
            tomas_2 = os.path.join(cls.current_directory +
                                   "\static\\tomas_photos", "2.png")
            tomas_3 = os.path.join(cls.current_directory +
                                   "\static\\tomas_photos", "3.png")
            tomas_4 = os.path.join(cls.current_directory +
                                   "\static\\tomas_photos", "4.png")
            
            david_1 = os.path.join(cls.current_directory +
                                   "\static\\david_photos", "1.png")
            david_2 = os.path.join(cls.current_directory +
                                   "\static\\david_photos", "2.png")
            david_3 = os.path.join(cls.current_directory +
                                   "\static\\david_photos", "3.png")
            david_4 = os.path.join(cls.current_directory +
                                   "\static\\david_photos", "4.png")
            
            jorge_1 = os.path.join(cls.current_directory +
                                   "\static\\jorge_photos", "1.png")
            jorge_2 = os.path.join(cls.current_directory +
                                   "\static\\jorge_photos", "2.png")
            jorge_3 = os.path.join(cls.current_directory +
                                   "\static\\jorge_photos", "3.png")
            jorge_4 = os.path.join(cls.current_directory +
                                   "\static\\jorge_photos", "4.png")

            image_paths = [
                # Pack de imagenes 1
                tomas_1,
                tomas_2,
                tomas_3,
                tomas_4,
                # Pack de imagenes 2
                david_1,
                david_2,
                david_3,
                david_4,
                # Pack de imagenes 3
                route_logo,
                route_logo,
                route_image,
                route_logo,
                # Pack de imagenes 4
                jorge_1,
                jorge_2,
                jorge_3,
                jorge_4,
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
                current_image_index = (cls.image_index + i)

                # Cargar la imagen actual
                image_path = image_paths[current_image_index]
                image = PhotoImage(file=image_path)

                # Crear un sub-frame para mostrar la imagen
                subframe = Frame(
                    bottom_right_frame, bg="white", borderwidth=1, relief="groove")
                subframe.grid(row=0, column=i, sticky="nsew")

                # Crear un label para mostrar la imagen
                image_label = Label(subframe, image=image, cursor="cross")
                image_label.image = image
                image_label.pack(expand=True, fill="both")

            # Crear sub-frame para las imágenes inferiores
            for i in range(0, 2):
                # Calcular el índice de la imagen actual
                current_image_index = (cls.image_index + i + 2)

                # Cargar la imagen actual
                image_path = image_paths[current_image_index]
                image = PhotoImage(file=image_path)

                # Crear un sub-frame para mostrar la imagen
                subframe = Frame(
                    bottom_right_frame, bg="white", borderwidth=1, relief="groove")
                subframe.grid(row=1, column=i, sticky="nsew")

                # Crear un label para mostrar la imagen
                image_label = Label(subframe, image=image, cursor="cross")
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

        # Cargar objetos al sistema
        Deserializador.deserializar("Usuarios")
        Deserializador.deserializar("Cuentas")
        Deserializador.deserializar("Estados")
        Deserializador.deserializar("Bancos")
        Deserializador.deserializar("Metas")
        Deserializador.deserializar("Movimientos")

        estado1 = Estado()
        banco1 = Banco(estado=estado1)
        banco2 = Banco(estado=estado1, nombre="Banco prueba 1")
        banco3 = Banco(estado=estado1, nombre="Banco prueba 2")
        user1 = Usuario(_nombre="Jaime Guzman", _correo="JaimeGuzman@mail", _contrasena="12345")
        user1.asociarBanco(banco1)
        user1.setSuscripcion(Suscripcion.BRONCE)
        cuenta1 = Corriente(banco = banco1, clave = 1234, nombre = "Visa", divisa = Divisas.COP)
        cuenta2 = Corriente(banco = banco1, clave = 1234, nombre = "Master", divisa = Divisas.COP)
        cuenta2.setDisponible(800000)
        cuenta3 = Ahorros(banco = banco1, clave = 1234, nombre = "Cuenta ahorros prueba", divisa = Divisas.COP, saldo = 100)
        cuenta4 = Ahorros(banco = banco1, clave = 1234, nombre = "Cuenta ahorros prueba 1", divisa = Divisas.COP, saldo = 500)
        user1.asociarCuenta(cuenta1)
        user1.asociarCuenta(cuenta2)
        user1.asociarCuenta(cuenta3)
        user1.asociarCuenta(cuenta4)
        userGota = Usuario(_nombre="gotaGota", _correo="gotagota@mail", _contrasena="1234", _suscripcion=Suscripcion.DIAMANTE)
        userImpuestosPortafolio = Usuario(_nombre="impuestosPortafolio", _correo="impuestosPortafolio@mail", _contrasena="1234", _suscripcion=Suscripcion.DIAMANTE)
        cuenta7 = Ahorros(banco = banco1, clave = 1234, nombre = "Ahorros Gota", divisa = Divisas.COP, saldo = 10000000)
        cuenta8 = Ahorros(banco = banco1, clave = 1234, nombre = "Ahorros Portafolio", divisa = Divisas.COP, saldo = 100000)
        userGota.asociarCuenta(cuenta7)
        userImpuestosPortafolio.asociarCuenta(cuenta8)
        meta1 = Metas(nombre = "Carro", cantidad = 100, fecha = "10/10/2025")
        user1.asociarMeta(meta1)
        movimiento1 = Movimientos(cantidad = 0, categoria = Categoria.TRANSPORTE, fecha = datetime.now(), origen = cuenta3, destino = cuenta4 )
        user1.asociarMovimiento(movimiento1)
        Serializador.serializar("Usuarios")
        Serializador.serializar("Bancos")
        Serializador.serializar("Estados")
        Serializador.serializar("Cuentas")
        Serializador.serializar("Movimientos")
        Serializador.serializar("Metas")

        # Configuración básica de parámetros de la ventana de inicio
        cls.initial_window = Tk()
        cls.initial_window.geometry("1000x800")
        cls.initial_window.title("Mis Finanzas")
        #cls.initial_window.resizable(0, 0)
        route_logo = os.path.join(cls.current_directory + "\static", "logo.png")
        logo = PhotoImage(file=route_logo)
        cls.initial_window.iconphoto(True, logo)


        # Configuración básica de los parámetros del main_frame en la ventana de inicio
        main_frame = Frame(cls.initial_window, bg="#DFDEDE")
        main_frame.pack(fill="both", expand=True)

        # Configuración de los sub-frames anidados al main_frame de la ventana de inicio
        upper_frame = Frame(main_frame, bg="black",
                              borderwidth=1, relief="solid")
        upper_frame.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

        # Configuración de menú de inicio
        home_menu = Menu(upper_frame, cursor="cross")
        menu_options = Menu(home_menu, tearoff=0)
        menu_options.add_command(label="Descripción del sistema", command=show_description,
                                 activebackground="gray", activeforeground="white")
        menu_options.add_command(label="Salir de la aplicación", command=exit_initial_window,
                                 activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Inicio", menu=menu_options,
                              activebackground="gray", activeforeground="white")
        cls.initial_window.config(menu=home_menu)

        left_frame = Frame(main_frame, bg="white",
                             borderwidth=1, relief="solid")
        left_frame.place(anchor="w", relheight=0.85,
                        relwidth=0.46, rely=0.55, relx=0.03)

        right_frame = Frame(main_frame, bg="white",
                              borderwidth=1, relief="solid")
        right_frame.place(anchor="e", relheight=0.85,
                         relwidth=0.46, relx=0.97, rely=0.55)

        # Configuración de los sub-frames anidados a cada uno de los sub-frames anidados a main_frame de la ventana de inicio
        upper_left_frame = Frame(
            left_frame, bg="#DFDEDE", borderwidth=1, relief="groove")
        upper_left_frame.place(anchor="n", relheight=0.30,
                             relwidth=0.993, rely=0, relx=0.5)

        bottom_left_frame = Frame(
            left_frame, bg="white", borderwidth=1, relief="groove")
        bottom_left_frame.place(anchor="s", relheight=0.70,
                              relwidth=0.993, rely=0.998, relx=0.5)

        upper_right_frame = Frame(
            right_frame, bg="white", borderwidth=1, relief="groove")
        upper_right_frame.place(anchor="n", relheight=0.35,
                              relwidth=0.993, rely=0.002, relx=0.5)

        bottom_right_frame = Frame(
            right_frame, bg="white", borderwidth=1, relief="groove")
        bottom_right_frame.place(anchor="s", relheight=0.642,
                               relwidth=0.993, rely=0.998, relx=0.5)

        # Configuración de los nodos que se ubicarán en los sub-frames anidados a cada uno de los sub-frames anidados a main_frame de la ventana de inicio

        # -------Texto de título(upper_frame)---------------------
        upper_label_text_variable = "Mis Finanzas"
        upper_text_font_style = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        title_label = Label(upper_frame, text=upper_label_text_variable, fg="white",
                               bg="#94B43B", wraplength=400, font=upper_text_font_style, width=50)
        title_label.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        # --------------------------------------------------
        # -------Imágen del título(upper_frame)---------------------
        route_image = os.path.join(cls.current_directory + "\static", "unal.png")
        upper_image = PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = Label(upper_frame, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)
        # --------------------------------------------------
        # -------Texto de bienvenida(P3 - upper_left_frame)---------------------
        welcome_text_text_variable = "Bienvenidos al sistema de gestión financiera Mis Finanzas programado por: \n->Juan Pablo Mejía Gómez.\n->Leonard David Vivas Dallos.\n->José Daniel Moreno Ceballos.\n->Tomás Escobar Rivera.\n->Jorge Humberto García Botero."
        welcome_text = Text(upper_left_frame, cursor="cross", fg="black", bg="white", font=("Alegreya Sans", 12), wrap="word", spacing1=8, border=1, relief="groove")
        welcome_text.insert(INSERT, welcome_text_text_variable)
        welcome_text.tag_configure("justifying", justify="center")
        welcome_text.tag_add("justifying", "1.0", END)
        welcome_text.config(state="disabled")
        welcome_text.pack(expand=True, fill="both",
                           anchor="s")
        # --------------------------------------------------
        # -------Hoja de vida de los desarrolladores(P5 - upper_right_frame)---------------------
        # Crear el botón y asociar la función change_button_text con él
        button_developers_text = StringVar(
            upper_right_frame, "1. Tomas Escobar Rivera.\n Soy un apasionado programador con experiencia en múltiples lenguajes de programación. Soy una persona comprometida, organizada y con habilidades de trabajo en equipo. Me adapto rápidamente a nuevos entornos y disfruto de los desafíos que suponen resolver problemas complejos mediante el uso de la programación.")
        button_developers = Button(upper_right_frame, textvariable=button_developers_text, bg="white", command=change_button_text, font=(
            "Alegreya Sans", 12), activebackground="gray", activeforeground="white", border=1, relief="groove", cursor="cross", wraplength=450)
        style = font.Font(family="Times New Roman", size=12)
        button_developers.config(
            font=style, bg="#f8e5c7", border=2, relief="raised")
        button_developers.pack(expand=True, fill="both")
        # --------------------------------------------------
        # -------Fotos de los desarrolladores(P6 - bottom_right_frame)---------------------
        image_label = Label(bottom_right_frame, cursor="cross")
        image_label.pack(expand=True, fill="both")
        update_image()
        # --------------------------------------------------
        # -------Imágenes asociadas al sistema(P4 - bottom_left_frame)---------------------
        # Crear un label para mostrar la imagen.
        system_image_label = Label(
            bottom_left_frame, border=2, relief="groove", cursor="cross")
        system_image_label.place(
            anchor="n", relheight=.5, relwidth=.99, relx=0.5, rely=0.01)

        # Lista de las imágenes asociadas al sistema
        image_paths = [
            route_image,
        ]

        # Cargar las imágenes
        images = [PhotoImage(file=image_path) for image_path in image_paths]

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
        login_label = Label(bottom_left_frame, text="Ingresa tus datos para iniciar sesión: ",
                               fg="white", bg="#76232F", border=1, relief="sunken", font=style)
        login_label.place(anchor="n", relheight=.1,
                          relwidth=.99, relx=0.5, rely=0.51)
        # Crear un label con el usuario ó el correo.
        user_email_label = Label(bottom_left_frame, text="Usuario/Correo: ",
                                    fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_label.place(anchor="n", relheight=.20,
                               relwidth=.3, relx=0.156, rely=0.61)
        # Crear un entry para recibir el usuario ó el correo del usuario.
        user_email_entry = Entry(
            bottom_left_frame, fg="white", bg="black", border=1, relief="sunken", font=style)
        user_email_entry.place(anchor="n", relheight=.20,
                               relwidth=.45, relx=0.5, rely=0.61)
        # Crear un label con el usuario ó el correo.
        password_label = Label(bottom_left_frame, text="Contraseña: ",
                                  fg="white", bg="black", border=1, relief="sunken", font=style)
        password_label.place(anchor="n", relheight=.19,
                             relwidth=.3, relx=0.156, rely=0.81)
        # Crear un entry para recibir la contraseña del usuario.
        password_entry = Entry(
            bottom_left_frame, fg="white", bg="black", border=1, relief="sunken", font=style)
        password_entry.place(anchor="n", relheight=.19,
                             relwidth=.45, relx=0.5, rely=0.81)
        # Crear un botón para iniciar sesión.
        login_button = Button(bottom_left_frame, fg="white", bg="black", border=1, relief="sunken",
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
        
        # Método para crear cuenta en la ventana principal
        def create_account_user():
            def create_account_fuctionality_logic():
                inserted_values_entries = account_creation_ff.getValoresInsertados()
                label_account.destroy()
                button_continue.destroy()
                try:
                    for inserted_value_entry in inserted_values_entries:
                        if(inserted_value_entry.winfo_name() == "nombredelbanco"):
                            selected_bank = inserted_value_entry.get()
                            c = True
                            try:
                                for bank in Banco.getBancosTotales():
                                    if(selected_bank == bank.getNombre()):
                                        selected_bank = bank
                                        c = False
                                        break
                                if c:
                                    raise genericException.ValueNotFoundException()
                            except genericException.ValueNotFoundException:
                                confirmation = messagebox.askyesno("Mis finanzas", genericException.ValueNotFoundException.show_message())
                                if confirmation:
                                    create_account_user()
                                    break
                                else:
                                    back_menu_main()
                                    break
                        elif(inserted_value_entry.winfo_name() == "ahorrosócorriente"):
                            selected_account_type = inserted_value_entry.get()
                            try:
                                if(selected_account_type.lower().replace(" ", "") == "ahorros"):
                                    selected_account_type = Ahorros
                                elif(selected_account_type.lower().replace(" ", "") == "corriente"):
                                    selected_account_type = Corriente
                                else:
                                    raise genericException.ValueNotFoundException()
                            except genericException.ValueNotFoundException: 
                                confirmation = messagebox.askyesno("Mis finanzas", genericException.ValueNotFoundException.show_message())
                                if confirmation:
                                    create_account_user()
                                    break
                                else:
                                    back_menu_main()
                                    break
                        elif(inserted_value_entry.winfo_name() == "clavedelacuenta"):
                            selected_password = str(inserted_value_entry.get())
                            try:
                                if(len(selected_password) == 0 or selected_password.count(" ") != 0):
                                    raise genericException.BadFormatException()
                            except genericException.BadFormatException:
                                confirmation = messagebox.askyesno("Mis finanzas", genericException.BadFormatException.show_message())
                                if confirmation:
                                    create_account_user()
                                    break
                                else:
                                    back_menu_main()
                                    break
                        elif(inserted_value_entry.winfo_name() == "divisa"):
                            selected_currency = str(inserted_value_entry.get()).upper()
                            currencies_list = Divisas.getDivisas()
                            c = True
                            try:
                                for currency in currencies_list:
                                    if(selected_currency == currency.value):
                                        selected_currency = currency
                                        c = False
                                        break
                                if c:
                                    raise genericException.ValueNotFoundException()
                            except genericException.ValueNotFoundException:
                                confirmation = messagebox.askyesno("Mis finanzas", genericException.ValueNotFoundException.show_message())
                                if confirmation:
                                    create_account_user()
                                    break
                                else:
                                    back_menu_main()
                                    break
                        elif(inserted_value_entry.winfo_name() == "nombredelacuenta"):
                            selected_name = str(inserted_value_entry.get())
                            try:
                                if(len(selected_name) == 0):
                                    raise genericException.BadFormatException()
                            except genericException.BadFormatException:
                                confirmation = messagebox.askyesno("Mis finanzas", genericException.BadFormatException.show_message())
                                if confirmation:
                                    create_account_user()
                                    break
                                else:
                                    back_menu_main()
                                    break

                    account_creation_ff.getFieldFrameObject().destroy()
                    if(selected_account_type == Ahorros):
                        label_account_result = Label(account_creation_frame, text="La cuenta se ha creado exitosamente: " + str(cls.user.asociarCuenta(Ahorros(banco=selected_bank, clave=selected_password, divisa=selected_currency, nombre=selected_name))), font=style_account_creation, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                    elif(selected_account_type == Corriente):
                         label_account_result = Label(account_creation_frame, text="La cuenta se ha creado exitosamente: " + str(cls.user.asociarCuenta(Corriente(banco=selected_bank, clave=selected_password, divisa=selected_currency, nombre=selected_name))), font=style_account_creation, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                    label_account_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                    button_result = Button(account_creation_frame, text="Volver al menú principal", font=style_account_creation, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                    button_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                except UnboundLocalError:
                    pass

            titulo_funcionalidad.set("Creación de cuenta.")
            descripcion_funcionalidad.set("¡Hola! {} anímate a crear una cuenta.".format(cls.user.getNombre()))
            style_account_creation=font.Font(cls.main_window, family="Times New Roman", size=14)
            account_creation_frame = Frame(cls.subframe_main, bg="gray", borderwidth=1, relief="solid")
            account_creation_frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)  
            try:
                if(len(cls.user.getCuentasAsociadas()) >= cls.user.getLimiteCuentas()):
                    raise accountsException.MaxLimitAccountsReached(cls.user)
            except accountsException.MaxLimitAccountsReached:
                confirmation = messagebox.askyesno("Mis finanzas", accountsException.MaxLimitAccountsReached(cls.user).show_message())
                if confirmation:
                    comprobar_suscripcion()
                else:
                    back_menu_main()
            else:       
                label_account = Label(account_creation_frame, text="Para crear una nueva cuenta, favor diligencie los siguientes datos: ", font=style_account_creation, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                label_account.grid(row=0, column=0, columnspan=2, sticky="NSEW", padx=2, pady=2)
                account_creation_ff = FieldFrame(tituloCriterios="Datos", criterios=["Nombre del banco", "Ahorros ó corriente", "Clave de la cuenta", "Divisa", "Nombre de la cuenta"], tituloValores="Valores", frame=[account_creation_frame, 1, 0, 2, 1])
                button_continue = Button(account_creation_frame, text="Continuar", font=style_account_creation, command=create_account_fuctionality_logic, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                button_continue.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)

        # Método para salir de la ventana principal
        def exit_principal_window():
            # Guardar objetos del sistema
            Serializador.serializar("Usuarios")
            Serializador.serializar("Cuentas")
            Serializador.serializar("Estados")
            Serializador.serializar("Bancos")
            Serializador.serializar("Metas")
            Serializador.serializar("Movimientos")

            cls.user = None
            cls.main_window.destroy()
            App.start_initial_window()

        # Método que muestra la descripción del sistema.
        def show_description():
            messagebox.showinfo("Mis Finanzas", "Mis Finanzas es una plataforma de gestión financiera digital que brinda a los usuarios la capacidad de administrar y controlar sus recursos monetarios de manera eficiente. El propósito fundamental de Mis Finanzas es mejorar la relación que las personas tienen con su dinero, proporcionando diversas funcionalidades diseñadas para ofrecer a los usuarios una amplia gama de opciones sobre cómo utilizar sus fondos y obtener el máximo beneficio de ellos. Esta plataforma permite a los usuarios realizar un seguimiento detallado de sus ingresos, gastos y ahorros, brindando una visión integral de su situación financiera. Además, ofrece herramientas para establecer y monitorear metas financieras, como ahorros para un objetivo específico o la realización de préstamos.")
        
        # Metódo que muestra información adicional del sistema
        def about():
            messagebox.showinfo("Mis Finanzas","Desarrollado por Juan Pablo Mejía Gómez, Leonard David Vivas Dallos, José Daniel Moreno Ceballos, Tomás Escobar Rivera y Jorge Humberto García Botero.\nEste programa ha sido desarrollado por el equipo 9 del grupo 2 con el objetivo de aplicar los conceptos aprendidos para el manejo de excepciones e interfaces gráficas. \nAgradecemos su interés y confianza al utilizar nuestro programa. Hemos invertido tiempo y esfuerzo para brindarte una herramienta funcional y confiable que esperamos que satisfaga los requerimientos exigidos. \nNos encantaría recibir tus comentarios y sugerencias para mejorar aún más este programa.")
        
        # Método que muestra las cuentas de ahorro asociadas al cls.user
        def show_saving_accounts_user(master, function, style, row_number):
            try:
                asociated_accounts_user = cls.user.mostrarCuentasAhorroAsociadas()
            except accountsException.NoSavingAccountsAssociatedException:
                    messagebox.showerror("Mis finanzas", accountsException.NoSavingAccountsAssociatedException(cls.user).show_message())
                    back_menu_main()
            else:
                    master.columnconfigure(2, weight=1)
                    master.columnconfigure(0, weight=1)
                    label_accounts_options = Label(master = master, text = "Seleccione una cuenta de la lista de cuentas de ahorro asociadas al usuario {}:".format(cls.user.getNombre()), font = style, border=1, relief="solid", bg="#8C7566", fg="white")
                    label_accounts_options.grid(row=row_number, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                    selected_account = StringVar(master)
                    accounts_options_combobox = Combobox(master = master, textvariable=selected_account, cursor="cross", font=style)
                    accounts_options_combobox["values"] = [asociated_accounts_user[m].getNombre() for m in range(0, len(asociated_accounts_user))]
                    accounts_options_combobox['state'] = 'readonly'
                    accounts_options_combobox.grid(row=row_number + 1, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                    button_select = Button(master=master, text="Aceptar", command=function, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style)
                    button_select.grid(row=row_number, column=2, rowspan=2, padx=2, pady=2, sticky="NSEW")
            return [selected_account, label_accounts_options, accounts_options_combobox, button_select]
            
        # Método que muestra las cuentas de ahorro totales del sistema
        def show_saving_accounts_total(master, function, style, row_number):
            try:
                accounts_total = Ahorros.getCuentasAhorrosTotales()
            except accountsException.NoSavingAccountsAssociatedException:
                    messagebox.showerror("Mis finanzas", accountsException.NoSavingAccountsAssociatedException(cls.user).show_message())
                    back_menu_main()
            else:
                    master.columnconfigure(2, weight=1)
                    master.columnconfigure(0, weight=1)
                    label_accounts_options = Label(master = master, text = "Seleccione una cuenta de la lista de cuentas de ahorro totales del sistema:", font = style, border=1, relief="solid", bg="#8C7566", fg="white")
                    label_accounts_options.grid(row=row_number, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                    selected_account = StringVar(master)
                    accounts_options_combobox = Combobox(master = master, textvariable=selected_account, cursor="cross", font=style)
                    accounts_options_combobox["values"] = [accounts_total[m].getNombre() for m in range(0, len(accounts_total))]
                    accounts_options_combobox['state'] = 'readonly'
                    accounts_options_combobox.grid(row=row_number + 1, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                    button_select = Button(master=master, text="Aceptar", command=function, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style)
                    button_select.grid(row=row_number, column=2, rowspan=2, padx=2, pady=2, sticky="NSEW")
            return [selected_account, label_accounts_options, accounts_options_combobox, button_select]
        
        # Método que muestra los bancos asociados al cls.user
        def show_banks_user(master, function, style):
            try:
                asociated_banks_user = cls.user.mostrarBancosAsociados()
            except banksException.NoBanksAssociatedException:
                messagebox.showerror("Mis finanzas", banksException.NoBanksAssociatedException(cls.user).show_message())
                back_menu_main()
            else:
                master.columnconfigure(2, weight=1)
                master.columnconfigure(0, weight=1)
                label_banks_options = Label(master = master, text = "Seleccione un banco de la lista de bancos asociados al usuario {}:".format(cls.user.getNombre()), font = style, border=1, relief="solid", bg="#8C7566", fg="white")
                label_banks_options.grid(row=0, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                selected_bank = StringVar(master)
                banks_options_combobox = Combobox(master = master, textvariable=selected_bank, cursor="cross", font=style)
                banks_options_combobox["values"] = [asociated_banks_user[m].getNombre() for m in range(0, len(asociated_banks_user))]
                banks_options_combobox['state'] = 'readonly'
                banks_options_combobox.grid(row=1, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                button_select = Button(master=master, text="Aceptar", command=function, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style)
                button_select.grid(row=0, column=2, rowspan=2, padx=2, pady=2, sticky="NSEW")
            return [selected_bank, label_banks_options, banks_options_combobox, button_select]

        # Método para volver al menú inicial
        def back_menu_main():
            for frame in cls.subframe_main.winfo_children(): 
                if str(type(frame).__name__) == "Frame" and frame.winfo_name() != "subframe_description_title" and frame.winfo_name() != "subframe_description_component":
                    frame.destroy()
                    welcome_text_reset()

        # Método para reestablecer el mensaje de bienvenida de la ventana principal
        def welcome_text_reset():
            titulo_funcionalidad.set("Bienvenido " + cls.user.getNombre() + " a Mis Finanzas")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("Estamos encantados de ayudarte a aprovechar al máximo todas las funcionalidades que ofrecemos. A continuación, te enumeramos las disponibles: 1. Comprobar tu suscripción. 2. Invertir tu saldo. 3. Consignar saldo a tu cuenta. 4. Transferir saldo entre tus cuentas. 5. Compra con tu cuenta corriente. 6. Pedir un prestamo 7. Pagar un prestamo. 8. Asesoramiento de inversiones. 9. Compra de cartera.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")
            
            text_description_component.config(state="normal")
            basic_instructions.set("Estimado Usuario {}. Las instrucciones para utilizar el programa 'Mis finanzas' son: \n1. Si deseas volver a la Ventana de Inicio debes dirigirte al menú superior y seleccionar la opción Archivo, posteriormente, debes seleccionar la opción Cerrar sesión.\n2. Si deseas conocer información referente al funcionamiento de la aplicación debes drigirte al menú superior y seleccionar la opción Archivo, posteriormente, debes seleccionar la opción Aplicación.\n3. Si deseas acceder a alguna de las funcionalidades ofrecidas debes dirigirte al menú superior y seleccionar la opción Procesos Y Consultas, posteriormente, debes seleccionar la funcionalidad de tu interés. \n4. Si deseas conocer información adicional debes dirigirte al menú superior y seleccionar la opción Ayuda, posteriormente, debes seleccionar la opción Acerca de.".format(cls.user.getNombre()))
            text_description_component.delete("1.0", END)
            text_description_component.insert("1.0", basic_instructions.get())
            text_description_component.tag_configure("justifying", justify="center")
            text_description_component.tag_add("justifying", "1.0", END)
            text_description_component.config(state="disabled")

        # Metodos de las funcionalidades del menú
        def comprobar_suscripcion():
            titulo_funcionalidad.set("Funcionalidad - Modificar Suscripcion")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("El método de instancia comprobarSuscripcion que se encuentra en la clase Banco tiene como parámetro una instancia de la clase Usuario. En este método se consulta el atributo Suscripcion de la instancia de Usuario dada por parámetro y, con base en este, se modifica el atributo de instancia limiteCuentas de tipo int de la misma instancia de Usuario. Este atributo limiteCuentas se utiliza para establecer la cantidad de instancias diferentes de la clase Cuenta que se le pueden asociar a través del método de instancia asociarCuentas, que se encuentra dentro de la clase Usuario, a la misma instancia de Usuario pasada por parámetro. Estas cuentas son añadidas al atributo de instancia cuentasAsociadas de tipo list, que se encuentra dentro de la clase Usuario.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            style_suscription=font.Font(cls.main_window, family="Times New Roman", size=15)
            suscription_frame = Frame(cls.subframe_main, bg="#B3AF9B", borderwidth=1, relief="solid")
            suscription_frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)
            
            def start_functionality():
                def functionality_logic():
                    def yes_no_confirmation():
                        def modify_suscription_main():
                            selected_suscription = suscription_options_combobox.get()
                            label_suscription_options.destroy()
                            suscription_options_combobox.destroy()
                            button_select_yes_no.destroy()
                            try:
                                if(selected_suscription is None or selected_suscription == ""):
                                    raise suscriptionException.NoSuscriptionSelectedException
                                else:
                                    selected_suscription = Suscripcion.__getitem__(selected_suscription) 
                                    if(selected_suscription.getLimiteCuentas() < len(cls.user.getCuentasAsociadas())):
                                        raise suscriptionException.UnderAccountsLimitException
                            except suscriptionException.NoSuscriptionSelectedException:
                                confirmation = messagebox.askretrycancel("Mis finanzas", suscriptionException.NoSuscriptionSelectedException.show_message())
                                if confirmation:
                                    yes_no_confirmation()
                                else:
                                    back_menu_main()
                            except suscriptionException.UnderAccountsLimitException:
                                messagebox.showerror("Mis finanzas", suscriptionException.UnderAccountsLimitException(selected_suscription, cls.user).show_message())
                                back_menu_main()
                            else:
                                suscription_frame.columnconfigure(0, weight=1)
                                suscription_frame.columnconfigure(1, weight=0)
                                cls.user.setSuscripcion(selected_suscription)
                                cls.user.setLimiteCuentas(selected_suscription.getLimiteCuentas())
                                label_suscription_options.destroy()
                                suscription_options_combobox.destroy()
                                button_select_yes_no.destroy()
                                label_result = Label(suscription_frame, text="El nivel de suscripción del usuario " + cls.user.getNombre() + " se ha actualizado a " + cls.user.getSuscripcion().name, font=style_suscription, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                label_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                                button_result = Button(suscription_frame, text="Volver al menú principal", font=style_suscription, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                button_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)    
                        
                        suscription_frame.columnconfigure(0, weight=1)
                        suscription_frame.columnconfigure(1, weight=1)
                        label_message.destroy()
                        button_yes.destroy()
                        button_no.destroy()
                        label_suscription_options = Label(master = suscription_frame, text = "Seleccione un nivel de suscripción: ", font = style_suscription, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        label_suscription_options.grid(row=0, column=0, padx=2, pady=2, sticky="NSEW")
                        selected_suscription = StringVar(suscription_frame)
                        suscription_options_combobox = Combobox(master = suscription_frame, textvariable=selected_suscription, cursor="cross", font=style_suscription)
                        suscription_options_combobox["values"] = [Suscripcion.getNivelesSuscripcion()[m].name for m in range(0, len(Suscripcion.getNivelesSuscripcion())) if Suscripcion.getNivelesSuscripcion()[m] != cls.user.getSuscripcion()]
                        suscription_options_combobox['state'] = 'readonly'
                        suscription_options_combobox.grid(row=1, column=0, padx=2, pady=2, sticky="NSEW")
                        button_select_yes_no = Button(master=suscription_frame, text="Aceptar", command=modify_suscription_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style_suscription)
                        button_select_yes_no.grid(row=0, column=1, rowspan=2, padx=2, pady=2, sticky="NSEW")
        
                    selected_bank = banks_options_combobox.get()
                    label_banks_options.destroy()
                    banks_options_combobox.destroy()
                    button_select.destroy()
                    try:
                        if(selected_bank == "" or selected_bank is None):
                            raise banksException.NoBankSelectedException
                    except banksException.NoBankSelectedException:
                        confirmation = messagebox.askretrycancel("Mis finanzas", banksException.NoBankSelectedException.show_message())
                        if confirmation:
                            start_functionality()
                        else:
                            back_menu_main()       
                    else:
                        suscription_frame.columnconfigure(0, weight=1)
                        suscription_frame.columnconfigure(1, weight=1)
                        suscription_frame.columnconfigure(2, weight=0)
                        message = str(Banco(selected_bank).comprobarSuscripcion(cls.user))
                        label_message = Label(suscription_frame, text=message + "\n¿Desea cambiar su nivel de suscripción? (Y/N): ", font=style_suscription, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        label_message.grid(row=0, column=0, columnspan=2, sticky="NSEW", padx=2, pady=2)
                        button_yes = Button(suscription_frame, text="Si", font=style_suscription, command=yes_no_confirmation, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        button_yes.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                        button_no = Button(suscription_frame, text="No", font=style_suscription, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        button_no.grid(row=1, column=1, sticky="NSEW", padx=2, pady=2)
                
                objects = show_banks_user(suscription_frame, functionality_logic, style_suscription)
                selected_bank = objects[0]
                label_banks_options = objects[1]
                banks_options_combobox = objects[2]
                button_select = objects[3]

            start_functionality()
   
        def invertir_saldo():
            titulo_funcionalidad.set("Funcionalidad - Invertir Saldo")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("El método de instancia invertirSaldo que se encuentra en la clase Ahorros consulta el atributo de instancia titular de tipo Usuario, de la instancia de Ahorros utilizada para ejecutar el método, usando el operador self y el método de instancia getTitular. Posteriormente, verifica el atributo de instancia suscripcion de la instancia titular y obtiene la constante _PROBABILIDADINVERSION de tipo float asociada a este. Esta última constante se utiliza para realizar un cálculo aritmético que se almacena dentro de una variable de tipo double llamada rand, luego se evalúa que rand sea mayor ó igual a uno. Posteriormente, si la condición es true, entonces se retorna una instancia de la clase Movimientos, pero si la condición es false, entonces se levanta una excepción de tipo accountsException.FailedInvestmentException.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            style_balance_investment=font.Font(cls.main_window, family="Helvetica", size=14)
            balance_investment_frame = Frame(cls.subframe_main, bg="#DFDEDE", borderwidth=1, relief="solid")
            balance_investment_frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)    

            def start_functionality():
                def functionality_logic():
                    selected_account = accounts_options_combobox.get()
                    label_accounts_options.destroy()
                    accounts_options_combobox.destroy()
                    button_select.destroy()
                    try:
                        if(selected_account == "" or selected_account is None):
                            raise accountsException.NoAccountSelectedException
                        for account in cls.user.getCuentasAhorroAsociadas():
                            if(selected_account == account.getNombre()):
                                selected_account = account
                        if(selected_account.getSaldo() == 0.0):
                            raise accountsException.NoBalanceinSavingAccountException(selected_account)
                        c = selected_account.invertirSaldo()
                    except accountsException.NoBalanceinSavingAccountException:
                        confirmation = messagebox.askyesno("Mis finanzas", accountsException.NoBalanceinSavingAccountException(selected_account).show_message())
                        if confirmation:
                            consignar_saldo()
                        else:
                            back_menu_main()
                    except accountsException.FailedInvestmentException:
                        messagebox.showwarning("Mis finanzas", accountsException.FailedInvestmentException(cls.user).show_message())
                        back_menu_main()
                    except accountsException.NoAccountSelectedException:
                        confirmation = messagebox.askretrycancel("Mis finanzas", accountsException.NoAccountSelectedException.show_message())
                        if confirmation:
                            start_functionality()
                        else:
                            back_menu_main()
                    else:
                        balance_investment_frame.columnconfigure(0, weight=1)
                        balance_investment_frame.columnconfigure(2, weight=0)
                        cls.user.asociarMovimiento(c)
                        label_investment_result = Label(balance_investment_frame, text="La inversion de saldo ha sido exitosa " + str(c), font=style_balance_investment, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        label_investment_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                        label_movements_result = Label(balance_investment_frame, text=cls.user.verificarContadorMovimientos(), font=style_balance_investment, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        label_movements_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                        button_result = Button(balance_investment_frame, text="Volver al menú principal", font=style_balance_investment, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        button_result.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)
                
                objects = show_saving_accounts_user(balance_investment_frame, functionality_logic, style_balance_investment, 0)
                selected_account = objects[0]
                label_accounts_options = objects[1]
                accounts_options_combobox = objects[2]
                button_select = objects[3]

            start_functionality()

        def consignar_saldo():
            titulo_funcionalidad.set("Funcionalidad - Consignar Saldo")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("El método estático crearMovimiento que se encuentra en la clase Movimientos recibe como parámetros dos instancias de la clase Ahorros llamadas destino y origen, un enum de Categoria, un dato de tipo double llamado cantidad y un objeto de tipo datetime llamado fecha. Este método verifica que origen sea None y que la categoría sea diferente de Categoria.PRESTAMO, de ser así, entonces se retorna una instancia de la clase Movimientos que es luego asociada a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            style_consign_balance=font.Font(cls.main_window, family="Garamond", size=15)
            balance_consign_frame = Frame(cls.subframe_main, bg="#DFDEDE", borderwidth=1, relief="solid")
            balance_consign_frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)  
            
            def start_functionality():
                def functionality_logic():
                    def restart_functionality():
                        label_ask_balance.destroy()
                        balance_investment_ff.getFieldFrameObject().destroy()
                        button_continue.destroy()
                        button_back.destroy()
                        balance_consign_frame.columnconfigure(1, weight=0)
                        start_functionality()
                    
                    def consign_balance_main():
                        selected_balance = balance_investment_ff.getValoresInsertados()[0].get()
                        label_ask_balance.destroy()
                        button_continue.destroy()
                        button_back.destroy()
                        balance_investment_ff.getFieldFrameObject().destroy()
                        try:
                            if (selected_balance is None or selected_balance == ""):
                                raise genericException.NoValueInsertedException(float)
                            selected_balance = float(selected_balance)
                        except ValueError:
                            confirmation = messagebox.askretrycancel("Mis finanzas", "Debes insertar un número. ¿Deseas intentarlo de nuevo? ")
                            if confirmation:
                                functionality_logic()
                            else:
                                back_menu_main()
                        except genericException.NoValueInsertedException:
                            confirmation = messagebox.askretrycancel("Mis finanzas", genericException.NoValueInsertedException(int).show_message())
                            if confirmation:
                                functionality_logic()
                            else:
                                back_menu_main()
                        else:
                            balance_consign_frame.columnconfigure(0, weight=1)
                            balance_consign_frame.columnconfigure(1, weight=0)
                            balance_consign_frame.columnconfigure(2, weight=0)
                            consign_movement = Movimientos.crearMovimiento(selected_account, selected_balance, Categoria.OTROS, datetime.today())
                            cls.user.asociarMovimiento(consign_movement)
                            label_consign_result = Label(balance_consign_frame, text="La consignación de saldo ha sido exitosa: \n" + str(consign_movement), font=style_consign_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_consign_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                            label_movements_result = Label(balance_consign_frame, text=cls.user.verificarContadorMovimientos(), font=style_consign_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_movements_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                            button_result = Button(balance_consign_frame, text="Volver al menú principal", font=style_consign_balance, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            button_result.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)

                    if accounts_options_combobox is not None:
                        selected_account = accounts_options_combobox.get()
                    label_accounts_options.destroy()
                    accounts_options_combobox.grid_forget()
                    button_select.destroy()
                    try:
                        for account in cls.user.getCuentasAhorroAsociadas():
                            if(selected_account == account.getNombre()):
                                selected_account = account
                        if(selected_account == "" or selected_account is None):
                            raise accountsException.NoAccountSelectedException
                    except accountsException.NoAccountSelectedException:
                        confirmation = messagebox.askretrycancel("Mis finanzas", accountsException.NoAccountSelectedException.show_message())
                        if confirmation:
                            start_functionality()
                        else:
                            back_menu_main()
                    else:
                        balance_consign_frame.columnconfigure(0, weight=1)
                        balance_consign_frame.columnconfigure(1, weight=1)
                        balance_consign_frame.columnconfigure(2, weight=0)
                        label_ask_balance = Label(balance_consign_frame, text="Ingrese el monto de su consignación de saldo: ", font=style_consign_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        label_ask_balance.grid(row=0, column=0, columnspan=2, sticky="NSEW", padx=2, pady=2)
                        balance_investment_ff = FieldFrame("Datos", ["Saldo"], "Valores", frame=[balance_consign_frame, 1, 0, 2, 1])
                        button_continue = Button(balance_consign_frame, text="Continuar", font=style_consign_balance, command=consign_balance_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        button_continue.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)
                        button_back = Button(balance_consign_frame, text="Volver", font=style_consign_balance, command=restart_functionality, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                        button_back.grid(row=2, column=1, sticky="NSEW", padx=2, pady=2)
                
                objects = show_saving_accounts_user(balance_consign_frame, functionality_logic, style_consign_balance, 0)
                selected_account = objects[0]
                label_accounts_options = objects[1]
                accounts_options_combobox = objects[2]
                button_select = objects[3]       
            
            start_functionality()
            
        def transferir_saldo():
            text_description_title.config(state="normal")
            titulo_funcionalidad.set("Funcionalidad - Transferir Saldo")
            descripcion_funcionalidad.set("El método estático crearMovimiento que se encuentra en la clase Movimientos recibe como parámetros dos instancias de la clase Ahorros llamadas destino y origen, un enum de Categoria, un dato de tipo double llamado cantidad y un objeto de tipo datetime llamado fecha. Este método verifica que origen no sea None, de ser así, entonces se retorna una instancia de la clase Movimientos que es luego asociada a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            style_transfer_balance = font.Font(cls.main_window, family="Times New Roman", size=15)
            balance_transfer_frame = Frame(cls.subframe_main, bg="#F6FBD0", borderwidth=1, relief="solid")
            balance_transfer_frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0) 

            def start_functionality():

                def own_account_logic():
                    label_transfer_options.destroy()
                    button_own_account.destroy()
                    button_another_account.destroy()
                  
                    def own_account_functionality_logic():
                        def own_account_functionality_logic_destination():
                            def restart_functionality():
                                label_ask_transfer.destroy()
                                balance_transfer_ff.getFieldFrameObject().destroy()
                                button_continue.destroy()
                                button_back.destroy()
                                balance_transfer_frame.columnconfigure(1, weight=0)
                                start_functionality()
                                
                            def own_account_functionality_logic_transfer():
                                selected_balance = balance_transfer_ff.getValoresInsertados()[0].get()
                                label_ask_transfer.destroy()
                                button_continue.destroy()
                                button_back.destroy()
                                balance_transfer_ff.getFieldFrameObject().destroy()
                                try:
                                    if (selected_balance is None or selected_balance == ""):
                                        raise genericException.NoValueInsertedException(int)
                                    selected_balance = int(selected_balance)
                                except ValueError:
                                    confirmation = messagebox.askretrycancel("Mis finanzas", "Debes insertar un número. ¿Deseas intentarlo de nuevo? ")
                                    if confirmation:
                                        own_account_logic()
                                    else:
                                        back_menu_main()
                                except genericException.NoValueInsertedException:
                                    confirmation = messagebox.askretrycancel("Mis finanzas", genericException.NoValueInsertedException(int).show_message())
                                    if confirmation:
                                        own_account_logic()
                                    else:
                                        back_menu_main()
                                else:
                                    balance_transfer_frame.columnconfigure(0, weight=1)
                                    balance_transfer_frame.columnconfigure(1, weight=0)
                                    balance_transfer_frame.columnconfigure(2, weight=0)
                                    transfer_movement = Movimientos.crearMovimiento(selected_account_destination, selected_balance, Categoria.FINANZAS, datetime.today(), selected_account_origin)
                                    cls.user.asociarMovimiento(transfer_movement)
                                    label_transfer_result = Label(balance_transfer_frame, text="La transferencia de saldo ha sido exitosa: \n" + str(transfer_movement), font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    label_transfer_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                                    label_movements_result = Label(balance_transfer_frame, text=cls.user.verificarContadorMovimientos(), font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    label_movements_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                                    button_result = Button(balance_transfer_frame, text="Volver al menú principal", font=style_transfer_balance, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    button_result.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)

                            balance_transfer_frame.columnconfigure(2, weight=0)
                            selected_account_destination = accounts_options_combobox_local.get()
                            label_account_destination.destroy()
                            label_accounts_options_local.destroy()
                            accounts_options_combobox_local.destroy()
                            button_select_local.destroy()

                            for account_user in accounts_user:
                                if selected_account_destination == account_user.getNombre():
                                        selected_account_destination = account_user

                            label_ask_transfer = Label(balance_transfer_frame, text="Ingrese el monto de su consignación de saldo: ", font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_ask_transfer.grid(row=0, column=0, columnspan=2, sticky="NSEW", padx=2, pady=2)
                            balance_transfer_ff = FieldFrame("Datos", ["Saldo"], "Valores", frame=[balance_transfer_frame, 1, 0, 2, 1])
                            button_continue = Button(balance_transfer_frame, text="Continuar", font=style_transfer_balance, command=own_account_functionality_logic_transfer, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            button_continue.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)
                            button_back = Button(balance_transfer_frame, text="Volver", font=style_transfer_balance, command=restart_functionality, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            button_back.grid(row=2, column=1, sticky="NSEW", padx=2, pady=2)
          
                        selected_account_origin = accounts_options_combobox.get()
                        label_accounts_options.destroy()
                        accounts_options_combobox.destroy()
                        button_select.destroy()
                        for account in cls.user.getCuentasAhorroAsociadas():
                                if(selected_account_origin == account.getNombre()):
                                    selected_account_origin = account

                        try:
                            if(selected_account_origin.getSaldo() == 0):
                                raise accountsException.NoBalanceinSavingAccountException(selected_account_origin)
                        except accountsException.NoBalanceinSavingAccountException:
                            confirmation = messagebox.askyesno("Mis finanzas", accountsException.NoBalanceinSavingAccountException(selected_account_origin).show_message())
                            if(confirmation):
                                back_menu_main()
                                consignar_saldo()
                            else:
                                own_account_logic()
                        else:
                            accounts_user = cls.user.getCuentasAhorroAsociadas()
                            for account_user in accounts_user:
                                if selected_account_origin.getId() == account_user.getId():
                                        accounts_user.remove(account_user)
                            label_account_destination = Label(balance_transfer_frame, text="A cual de sus cuentas desea transferir su saldo: ",font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_account_destination.grid(row=0, column=0, columnspan=3, sticky="NSEW", padx=2, pady=2)
                            objects = show_saving_accounts_user(balance_transfer_frame, own_account_functionality_logic_destination, style_transfer_balance, 1)
                            label_accounts_options_local = objects[1]
                            accounts_options_combobox_local = objects[2]
                            button_select_local = objects[3]
                            accounts_user.append(selected_account_origin)
                    
                    try:
                        if(len(cls.user.getCuentasAhorroAsociadas()) < 2):
                            raise accountsException.NotEnoughSavingAccountsException(cls.user)
                    except accountsException.NotEnoughSavingAccountsException:
                        confirmation = messagebox.askyesno("Mis finanzas", accountsException.NotEnoughSavingAccountsException(cls.user).show_message())    
                        if(confirmation):
                            back_menu_main()
                            create_account_user()
                        else:
                             back_menu_main()
                    else:
                        objects = show_saving_accounts_user(balance_transfer_frame, own_account_functionality_logic, style_transfer_balance, 0)
                        label_accounts_options = objects[1]
                        accounts_options_combobox = objects[2]
                        button_select = objects[3]

                def another_account_logic():
                    label_transfer_options.destroy()
                    button_own_account.destroy()
                    button_another_account.destroy()

                    def another_account_functionality_logic():
                        def another_account_functionality_logic_destination():
                            def restart_functionality():
                                label_ask_transfer.destroy()
                                balance_transfer_ff.getFieldFrameObject().destroy()
                                button_continue.destroy()
                                button_back.destroy()
                                balance_transfer_frame.columnconfigure(1, weight=0)
                                start_functionality()

                            def another_account_functionality_logic_transfer():
                                selected_balance = balance_transfer_ff.getValoresInsertados()[0].get()
                                label_ask_transfer.destroy()
                                button_continue.destroy()
                                button_back.destroy()
                                balance_transfer_ff.getFieldFrameObject().destroy()
                                try:
                                    if (selected_balance is None or selected_balance == ""):
                                        raise genericException.NoValueInsertedException(int)
                                    selected_balance = int(selected_balance)
                                except ValueError:
                                    confirmation = messagebox.askretrycancel("Mis finanzas", "Debes insertar un número. ¿Deseas intentarlo de nuevo? ")
                                    if confirmation:
                                        own_account_logic()
                                    else:
                                        back_menu_main()
                                except genericException.NoValueInsertedException:
                                    confirmation = messagebox.askretrycancel("Mis finanzas", genericException.NoValueInsertedException(int).show_message())
                                    if confirmation:
                                        own_account_logic()
                                    else:
                                        back_menu_main()
                                else:
                                    balance_transfer_frame.columnconfigure(0, weight=1)
                                    balance_transfer_frame.columnconfigure(1, weight=0)
                                    balance_transfer_frame.columnconfigure(2, weight=0)
                                    transfer_movement = Movimientos.crearMovimiento(selected_account_destination, selected_balance, Categoria.FINANZAS, datetime.today(), selected_account_origin)
                                    cls.user.asociarMovimiento(transfer_movement)
                                    label_transfer_result = Label(balance_transfer_frame, text="La transferencia de saldo ha sido exitosa: \n" + str(transfer_movement), font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    label_transfer_result.grid(row=0, column=0, sticky="NSEW", padx=2, pady=2)
                                    label_movements_result = Label(balance_transfer_frame, text=cls.user.verificarContadorMovimientos(), font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    label_movements_result.grid(row=1, column=0, sticky="NSEW", padx=2, pady=2)
                                    button_result = Button(balance_transfer_frame, text="Volver al menú principal", font=style_transfer_balance, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                                    button_result.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)

                            balance_transfer_frame.columnconfigure(2, weight=0)
                            selected_account_destination = accounts_options_combobox_local.get()
                            label_account_destination.destroy()
                            label_accounts_options_local.destroy()
                            accounts_options_combobox_local.destroy()
                            button_select_local.destroy()

                            for account in accounts_total:
                                if selected_account_destination == account.getNombre():
                                        selected_account_destination = account

                            label_ask_transfer = Label(balance_transfer_frame, text="Ingrese el monto de su consignación de saldo: ", font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_ask_transfer.grid(row=0, column=0, columnspan=2, sticky="NSEW", padx=2, pady=2)
                            balance_transfer_ff = FieldFrame("Datos", ["Saldo"], "Valores", frame=[balance_transfer_frame, 1, 0, 2, 1])
                            button_continue = Button(balance_transfer_frame, text="Continuar", font=style_transfer_balance, command=another_account_functionality_logic_transfer, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            button_continue.grid(row=2, column=0, sticky="NSEW", padx=2, pady=2)
                            button_back = Button(balance_transfer_frame, text="Volver", font=style_transfer_balance, command=restart_functionality, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            button_back.grid(row=2, column=1, sticky="NSEW", padx=2, pady=2)
                        
                        selected_account_origin = accounts_options_combobox.get()
                        label_accounts_options.destroy()
                        accounts_options_combobox.destroy()
                        button_select.destroy()
                        for account in cls.user.getCuentasAhorroAsociadas():
                                if(selected_account_origin == account.getNombre()):
                                    selected_account_origin = account

                        try:
                            if(selected_account_origin.getSaldo() == 0):
                                raise accountsException.NoBalanceinSavingAccountException(selected_account_origin)
                        except accountsException.NoBalanceinSavingAccountException:
                            confirmation = messagebox.askyesno("Mis finanzas", accountsException.NoBalanceinSavingAccountException(selected_account_origin).show_message())
                            if(confirmation):
                                back_menu_main()
                                consignar_saldo()
                            else:
                                another_account_logic()
                        else:
                            accounts_total = Ahorros.getCuentasAhorrosTotales()
                            for account in cls.user.getCuentasAhorroAsociadas():
                                conf = account.__contains__(accounts_total)
                                if conf != None:
                                        accounts_total.pop(conf)
                            label_account_destination = Label(balance_transfer_frame, text="Seleccione la cuenta de ahorros destino donde deseas transferir saldo: ",font=style_transfer_balance, cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
                            label_account_destination.grid(row=0, column=0, columnspan=3, sticky="NSEW", padx=2, pady=2)
                            objects = show_saving_accounts_total(balance_transfer_frame, another_account_functionality_logic_destination, style_transfer_balance, 1)
                            label_accounts_options_local = objects[1]
                            accounts_options_combobox_local = objects[2]
                            button_select_local = objects[3]
                            for account in cls.user.getCuentasAhorroAsociadas():
                                accounts_total.append(account)
                    try:
                        if(len(Ahorros.getCuentasAhorrosTotales()) < 2):
                            raise accountsException.NotEnoughTotalSavingAccountsException()
                    except accountsException.NotEnoughTotalSavingAccountsException:
                        messagebox.showerror("Mis finanzas", accountsException.NotEnoughTotalSavingAccountsException().show_message())    
                        back_menu_main()
                    else:
                        objects = show_saving_accounts_user(balance_transfer_frame, another_account_functionality_logic, style_transfer_balance, 0)
                        label_accounts_options = objects[1]
                        accounts_options_combobox = objects[2]
                        button_select = objects[3]

                balance_transfer_frame.columnconfigure(1, weight=1)
                balance_transfer_frame.columnconfigure(0, weight=1)
                label_transfer_options = Label(master = balance_transfer_frame, text = "Por favor, elija el destino de la transferencia: ", font=style_transfer_balance, border=1, relief="solid", bg="#8C7566", fg="white")
                label_transfer_options.grid(row=0, column=0, columnspan=2, padx=2, pady=2, sticky="NSEW")
                button_own_account = Button(master=balance_transfer_frame, text="1. Cuenta propia", command=own_account_logic, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style_transfer_balance)
                button_own_account.grid(row=1, column=0, padx=2, pady=2, sticky="NSEW")  
                button_another_account = Button(master=balance_transfer_frame, text="2. Cuenta externa", command=another_account_logic, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white", font=style_transfer_balance)
                button_another_account.grid(row=1, column=1, padx=2, pady=2, sticky="NSEW")  

            start_functionality()

        def compra_corriente():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Comprar con cuenta corriente")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("Agregar la descripcion en el metodo compra_corriente y agregar aca el funcionamiento de su funcionalidad")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

        def asesoramiento_inversiones():
            cls.user.asociarMeta(Metas.getMetasTotales()[0])
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Asesoramiento de Inversiones")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("La funcionalidad da una recomendación de un portafolio de inversiones en base a las preferencias y características del usuario, como las fechas de sus metas y sus movimientos o el dinero que hay en sus cuentas. Además, provee herramientas que pretenden mejorar aún más la inversión para la satisfacción del usuario.")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            frame = Frame(cls.subframe_main, bg="#B3B6B7", borderwidth=1, relief="solid")
            frame.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)

            def comienzo():
                # Ocultar el botón "Comenzar"
                comenzar.pack_forget()

                # Eliminar todos los widgets del contenedor
                for widget in frame.winfo_children():
                    widget.destroy()

                # Crear etiqueta y menú desplegable
                label1 = Label(frame, text="Tolerancia a Riesgos:", font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label1.pack()
                
                global combobox
                texto1 = StringVar()
                combobox = Combobox(frame, textvariable=texto1, font=font.Font(family="Times New Roman", size=16))
                combobox['values'] = ('', 'Baja', 'Media', 'Alta')
                combobox.current(0)
                combobox.pack()

                # Crear etiqueta y campo de texto
                label2 = Label(frame, text="¿Cuánto dinero deseas invertir?:", font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label2.pack()
                
                global entry2
                texto2 = StringVar()
                entry2 = Entry(frame, textvariable=texto2, font=font.Font(family="Times New Roman", size=16), bg="white")
                entry2.pack()

                frame2 = Frame(frame, width=200, height=100, bg="#B3B6B7")
                frame2.pack()

                # Crear botón "Siguiente"
                Siguiente = Button(frame2, text="Siguiente", command=mostrar_siguiente, font=font.Font(family="Times New Roman", size=16), bg="white")
                Siguiente.place(relx=0.5, rely=0.5, anchor='s')

            # Crear botón "Comenzar"
            comenzar = Button(frame, text="Comenzar", command=comienzo, font=font.Font(family="Times New Roman", size=16), bg="white")
            comenzar.place(relx=0.5, rely=0.5, anchor=CENTER)
    
            def mostrar_siguiente():
                tolerancia_riesgos = combobox.get()
                monto_inversion = entry2.get()

                if tolerancia_riesgos == "" or monto_inversion == "":
                    messagebox.showerror("Error", "Debes llenar todos los campos.")
                    return
                try:
                    monto_inversion == int(monto_inversion)
                except ValueError:
                    messagebox.showerror("Error", "El campo '¿Cuánto dinero deseas invertir?' debe ser un número entero.")
                    return
                
                if tolerancia_riesgos not in ('Baja', 'Media', 'Alta'):
                    messagebox.showerror("Error", "El campo 'Tolerancia a Riesgos' solo acepta los valores 'Baja', 'Media' o 'Alta'.")
                    return
                
                global numero_portafolio
                numero_portafolio = Banco.retorno_portafolio(tolerancia_riesgos, int(monto_inversion))
                
                # Eliminar todos los widgets del contenedor
                for widget in frame.winfo_children():
                    widget.destroy()

                # Obtener los datos de la revisión de metas
                resultado = Metas.revision_metas(cls.user)
                nombre = resultado.getNombre()
                cantidad = resultado.getCantidad()
                fecha_normal = resultado.getFecha()

                # Mostrar el resultado por pantalla
                resultado_texto = f"Tienes una meta para una fecha muy próxima: {nombre}, {cantidad}, {fecha_normal}"
                label_siguiente = Label(frame, text=resultado_texto, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_siguiente.pack()

                # Agregar mensaje de confirmación para cambiar la fecha
                mensaje_confirmacion = "¿Desea cambiar la fecha de la meta?"
                label_confirmacion = Label(frame, text=mensaje_confirmacion, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_confirmacion.pack()

                # Crear botones "Sí" y "No" centrados
                botones_frame = Frame(frame, bg="#B3B6B7")
                botones_frame.pack()

                boton_si = Button(botones_frame, text="Sí", command=mostrar_campo_fecha, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_si.pack(side=LEFT, padx=10)

                boton_no = Button(botones_frame, text="No", command=no_cambiar_fecha_meta, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_no.pack(side=LEFT, padx=10)


            def mostrar_campo_fecha():
                global entry_fecha

                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje y campo de texto para la fecha
                mensaje_fecha = "Ingrese la nueva fecha de la meta (en el formato dd/mm/yyyy):"
                label_fecha = Label(frame, text=mensaje_fecha, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_fecha.pack()
                
                entry_fecha = Entry(frame, font=font.Font(family="Times New Roman", size=16), bg="white")
                entry_fecha.pack()

                # Crear botón "Guardar"
                boton_guardar = Button(frame, text="Guardar", command=guardar_fecha_meta, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_guardar.pack()

            def guardar_fecha_meta():
                
                if entry_fecha.get() == '':
                    messagebox.showerror("Error", "Debes llenar todos los campos.")
                    return
                
                try:
                    datetime.strptime(entry_fecha.get(), "%d/%m/%Y")
                except ValueError:
                    messagebox.showerror("Error", "La fecha debe estar en el formato dd/mm/yyyy.")
                    return
                
                Metas.cambio_fecha(Metas.revision_metas(cls.user), entry_fecha.get())
                nueva_fecha = entry_fecha.get()
                plazo_inversion = Metas.determinar_plazo(Metas.revision_metas(cls.user))

                mensaje_confirmacion = f"La fecha de la meta ha sido cambiada a: {nueva_fecha}\n"
                mensaje_confirmacion += f"Plazo de inversión: {plazo_inversion}"

                # Mostrar mensaje de confirmación
                messagebox.showinfo("Mensaje", mensaje_confirmacion)
                mostrar_advertencia()


            def no_cambiar_fecha_meta():
                plazo_inversion = Metas.determinar_plazo(Metas.revision_metas(cls.user))

                mensaje_confirmacion = f"La fecha de la meta no ha sido cambiada.\n"
                mensaje_confirmacion += f"Plazo de inversión: {plazo_inversion}"

                # Mostrar mensaje de confirmación
                messagebox.showinfo("Mensaje", mensaje_confirmacion)
                mostrar_advertencia()


            def mostrar_advertencia():
                # Eliminar widgets existentesa
                for widget in frame.winfo_children():
                    widget.destroy()

                mensaje_advertencia = "Advertencia: Con el fin de hacer un buen asesoramiento analizaremos sus movimientos para encontrar la categoría en la que más dinero ha gastado."
                label_advertencia = Label(frame, text=mensaje_advertencia, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_advertencia.pack()

                # Mostrar la categoría en la que más dinero se ha gastado
                mensaje_categoria = "La categoría en la que más dinero ha gastado es: " + Movimientos._nombre_categoria
                mensaje_categoria += " que suma un total de " + str(Movimientos._cantidad_categoria)
                mensaje_categoria += "\n¿Deseas crear una meta con el fin de ahorrar la misma cantidad que has gastado en esta categoría?"
                label_categoria = Label(frame, text=mensaje_categoria, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_categoria.pack()

                # Crear botones "Sí" y "No"
                botones_frame = Frame(frame, bg="#B3B6B7")
                botones_frame.pack()

                boton_si = Button(botones_frame, text="Sí", command=crear_meta_ahorro, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_si.pack(side=LEFT, padx=10)

                boton_no = Button(botones_frame, text="No", command=mostrar_recomendaciones, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_no.pack(side=LEFT, padx=10)

            def crear_meta_ahorro():
                messagebox.showinfo("Mensaje", "Usaremos tus datos para crear la meta. Luego vamos a priorizar esa meta respecto a las demás que tengas")
                Movimientos.analizar_categoria(Metas.determinar_plazo(Metas.revision_metas(cls.user)))
                messagebox.showinfo("Mensaje", "La meta ha sido creada y puesta como prioridad en tu lista de metas")
                # LLAMAR A VER_METAS
                mostrar_recomendaciones()

            def mostrar_recomendaciones():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de recomendaciones

                mensaje_recomendaciones = "En base a los datos recolectados, deberías invertir tu dinero en estos sectores:"
                if numero_portafolio == 1:
                    recomendaciones = "- Servicios de comunicación\n- Consumo discrecional\n- Bienes raíces"
                elif numero_portafolio == 2:
                    recomendaciones = -"- Productos básicos de consumo\n- Energía\n- Compañías de inteligencia artificial"
                elif numero_portafolio == 3:
                    recomendaciones = "- Finanzas\n- Cuidado de la salud\n- Servicios de comunicación"
                elif numero_portafolio == 4:
                    recomendaciones = "- Oro\n- Acciones industriales\n- Información tecnológica\n"
                elif numero_portafolio == 5:
                    recomendaciones = "- Materiales de construcción\n- Bienes raíces\n- Finanzas\n"
                elif numero_portafolio == 6:
                    recomendaciones = "- Cuidado de la salud\n- Utilidades\n- Comodidades\n"
                elif numero_portafolio == 7:
                    recomendaciones = "- Oro\n- Bonos gubernamentales a mediano plazo\n- Información tecnológica\n"
                elif numero_portafolio == 8:
                    recomendaciones = "- Compañías de inteligencia artificial\n- Bonos gubernamentales a largo plazo\n- Productos básicos de consumo\n"
                else:
                    recomendaciones = "No tenemos una recomendación para ti"

                label_recomendaciones = Label(frame, text=mensaje_recomendaciones, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_recomendaciones.pack()

                label_sectores = Label(frame, text=recomendaciones, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_sectores.pack()

                # Mostrar mensaje adicional
                nombre_banco = Banco.banco_portafolio(cls.user).getNombre()
                interes_portafolio = Banco.intereses_portafolio(Banco.banco_portafolio(cls.user), cls.user)
                mensaje_adicional = "Nota: Hay un banco asociado al portafolio: " + nombre_banco + ", con una tasa de interés del " + str(interes_portafolio) +"%"
                label_adicional = Label(frame, text=mensaje_adicional, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_adicional.pack()

                # Mostrar botón "Siguiente"
                boton_siguiente = Button(frame, text="Siguiente", command=mostrar_prestamo, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_siguiente.pack()


            def mostrar_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de préstamo
                mensaje_prestamo = "Finalmente, para mejorar aún más tu inversión te recomendamos hacer un préstamo. ¿Deseas hacer el préstamo?"
                label_prestamo = Label(frame, text=mensaje_prestamo, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_prestamo.pack()

                # Crear botones "Sí" y "No" centrados
                botones_frame = Frame(frame, bg="#B3B6B7")
                botones_frame.pack()

                boton_si = Button(botones_frame, text="Sí", command=hacer_prestamo, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_si.pack(side=LEFT, padx=10)

                boton_no = Button(botones_frame, text="No", command=no_hacer_prestamo, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_no.pack(side=LEFT, padx=10)

            def hacer_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de solicitud de préstamo
                mensaje_prestamo = "Las tasas de interés de los préstamos están muy altas, pero tenemos la solución perfecta para ti, aunque no sea la más correcta... Vas a hacer un préstamo con el usuario gota a gota."
                label_prestamo = Label(frame, text=mensaje_prestamo, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7", wraplength=frame.winfo_width())
                label_prestamo.pack()

                # Mostrar campo de texto para ingresar el monto del préstamo
                label_monto = Label(frame, text="Ingrese el monto que desea solicitar prestado:", font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_monto.pack()
                
                global entry_monto
                texto_monto = StringVar()
                entry_monto = Entry(frame, textvariable=texto_monto, font=font.Font(family="Times New Roman", size=16), bg="white")
                entry_monto.pack()
                
                # Crear botón "Guardar"
                boton_guardar = Button(frame, text="Guardar", command=guardar_monto_prestamo, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_guardar.pack()

            def guardar_monto_prestamo():
                
                if entry_monto.get() == "":
                    messagebox.showerror("Error", "Debes llenar todos los campos.")
                    return
                try:
                    entry_monto.get() == int(entry_monto.get())
                except ValueError:
                    messagebox.showerror("Error", "Debes ingresar un número entero.")
                    return
                cuenta_gota = Usuario.getUsuariosTotales()[Usuario.hallarUsuariogotaGota()].getCuentasAsociadas()[0]
                cuenta_usuario = Cuenta.gota_gota(entry_monto.get(), cls.user, cuenta_gota)
                cuenta_usuario.vaciar_cuenta(cuenta_gota)
                messagebox.showinfo("Mensaje", "Era una trampa, ahora el usuario gota a gota vació tu cuenta")
                no_hacer_prestamo()

            def no_hacer_prestamo():
                # Eliminar widgets existentes
                for widget in frame.winfo_children():
                    widget.destroy()

                # Mostrar mensaje de despedida
                mensaje_despedida = "Ha sido un placer asesorarte en este proceso, espero que nuestra recomendación haya sido de ayuda."
                label_despedida = Label(frame, text=mensaje_despedida, font=font.Font(family="Times New Roman", size=16), bg="#B3B6B7")
                label_despedida.pack()

                boton_reiniciar = Button(frame, text="Reiniciar", command=asesoramiento_inversiones, font=font.Font(family="Times New Roman", size=16), bg="white")
                boton_reiniciar.pack()

        def compra_cartera(cuenta = None):
            
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Compra Catera")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("Agregar la descripcion en el metodo compra_cartera y agregar aca el funcionamiento de su funcionalidad")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            #Desarrollo de la funcionalidad

            if cuenta == None:

                #Cambio prueba, a espera confirmación de la SERIALIZACIÓN
                cls.user.getCuentasCorrienteAsociadas()[0].setDisponible(800000)
                

                #Arreglo que almacena las cuentas con deuda alguna
                cuentasEnDeuda = cls.user.retornarDeudas()

                #Arreglo que almacena las cuentas asociadas a un usuario
                cuentasAux = cls.user.getCuentasAsociadas()

                cuentasAux1 = cls.user.getCuentasCorrienteAsociadas()

                #Comprobación de existencia de Cuentas Corriente por parte del Usuario
                if len(cuentasAux1) <= 1:
                    messagebox.showerror("Error", "El usuario " + cls.user.getNombre() + " no alcanza la cantidad de cuentas Corriente necesarias para desarrollar la funcionalidad, recuerda que para ejecutar una compra de cartera necesitas por lo menos dos cuentas Corriente, una de ellas con una Deuda.")
                    return
                
                if len(cuentasEnDeuda) == 0:
                    messagebox.showerror("Error", "El usuario " + cls.user.getNombre() + " no tiene préstamos asociados, no es posible realizar la funcionalidad.")
                    return

                framecc = Frame(cls.subframe_main, borderwidth=1, relief="solid")
                framecc.place(relheight=0.75, relwidth=1, rely=0.25, relx=0)

                #Atributos auxiliares para cada función
                cuentas_capaces_deuda = []
                deuda = 0
                tasacion_cuentas = []

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
                            eleccion_compra()

                impresion_1 = "Cuentas a nombre de " + cls.user.getNombre() + " con préstamos asociados: "
                label_impresion = Label(framecc, text=impresion_1)
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

                def eleccion_compra():
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
                    label_impresion_2 = Label(framecc, text=impresion_2)
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

                    if confirmacion_Destino:
                        periodicidad()
                    
                def periodicidad():

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
                            label_impresion_3 = Label(framecc, text=impresion_3)
                            label_impresion_3.grid(row=0, column=0, columnspan=5)

                        cuen_comb_3 = ["Seleccionar Cuotas", "1 Cuota", "6 Cuotas", "12 Cuotas", "18 Cuotas", "24 Cuotas", "36 Cuotas", "48 Cuotas"]

                        eleccion_cuenta_compra = Combobox(framecc, values= cuen_comb_3)
                        eleccion_cuenta_compra.set("Seleccionar Cuotas")
                        eleccion_cuenta_compra.bind("<<ComboboxSelected>>", eleccion_3)
                        eleccion_cuenta_compra.grid(row = 3, column= 2)

                        while confirmacion_Periodicidad:
                            vista_previa()
                
                def vista_previa():
                    vistaPrevia = Corriente.vistaPreviaMovimiento(cuentas_capaces_deuda[cuenta_Destino - 1], eleccion_periodicidad, deuda, tasacion_cuentas[cuenta_Destino - 1])

                    cuota = []
                    message_pago = "¿Desea pagar intereses en el primer mes? Tenga en cuenta que de no hacerlo, en el segundo mes deberá pagar su valor correspondiente."
                    validacion_pago = messagebox.askyesno("Elección", message= message_pago)
                    if validacion_pago:
                        cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getCupo() - vistaPrevia.getDisponible())
                        vistaPrevia.setPrimerMensualidad(True)
                    else:
                        cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getCupo() - vistaPrevia.getDisponible(), 1)
                        vistaPrevia.setPrimerMensualidad(False)
                    
                    for widget in framecc.winfo_children():
                        widget.destroy()
                    
                    #Vista Previa de los resultados del cambio
                    impresion_4 = "Vista previa de como quedaría la cuenta escogida para recibir la deuda: "
                    label_impresion_4 = Label(framecc, text=impresion_4)
                    label_impresion_4.grid(row=0, column=0, columnspan=11)

                    label_impresion_5 = Label(framecc, text=vistaPrevia)
                    label_impresion_5.grid(row=2, column=0, columnspan=11)

                    cuotaMensual = Corriente.imprimirCuotaMensual(cuota)

                    label_impresion_6 = Label(framecc, text="Primer Cuota: ")
                    label_impresion_6.grid(row=3, column=0, columnspan=11)

                    impresion_8 = cuotaMensual + " " + vistaPrevia.getDivisa()
                    label_impresion_7 = Label(framecc, text=impresion_8)
                    label_impresion_7.grid(row=4, column=0, columnspan=11)

                    message_pago = "¿Desea un resumen completo de las cuotas a pagar?"
                    validacion_resumen = messagebox.askyesno("Elección", message= message_pago)
                    if validacion_resumen:
                        cuota_calculadora = []
                        if vistaPrevia.getPrimerMensualidad():
                            cuota_calculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses())
                        else:
                            cuota_calculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses(), True)
                        info_adicional = Corriente.informacionAdicionalCalculadora(cuota_calculadora, vistaPrevia.getCupo() - vistaPrevia.getDisponible())
                        calculadora_financiera(cuota_calculadora, info_adicional, vistaPrevia.getDivisa())
                    
                    message_confirmacion = "¿Desea confirmar la realización del movimiento?"
                    validacion_confirmacion = messagebox.askyesno("Elección", message= message_confirmacion)
                    if validacion_confirmacion:
                        #Cambios para la cuenta origen
                        cuentasEnDeuda[cuenta_Compra - 1].setDisponible(cuentasEnDeuda[cuenta_Compra - 1].getCupo())
                        cuentasEnDeuda[cuenta_Compra - 1].setPlazo_Pago(Cuotas.C1)

                        #Cambios para la cuenta destino
                        Cuenta.getCuentasTotales().remove(cuentas_capaces_deuda[cuenta_Destino - 1])
                        cls.user.getCuentasAsociadas().remove(cuentas_capaces_deuda[cuenta_Destino - 1])
                        cls.user.getCuentasCorrienteAsociadas().remove(cuentas_capaces_deuda[cuenta_Destino - 1])
                        Corriente.getCuentasCorrienteTotales().remove(cuentas_capaces_deuda[cuenta_Destino - 1])

                        cls.user.asociarCuenta(vistaPrevia)

                        messagebox.showinfo("Finalizado", "Compra de cartera realizada con éxito")
                    
                    else:
                        Cuenta.getCuentasTotales().remove(vistaPrevia)
                        Corriente.getCuentasCorrienteTotales().remove(vistaPrevia)
                        vistaPrevia = None

                        messagebox.showinfo("Cancelación", "Movimiento cancelado.")

                    return

                

            else:
                pass

        def calculadora_financiera():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Calculadora Financiera")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("Agregar la descripcion en el metodo calculadora_financiera y agregar aca el funcionamiento de su funcionalidad")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")
    
        def pedir_prestamo():

            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Pedir Prestamo")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("¿Necesitas dinero? realiza un prestamo con tu banco.\nPara pedir un prestamo es necesario que cuentes con una cuenta de ahorros, la cantidad de dinero que puedes prestar va a depender de tu nivel de suscripción y del banco asociado a tu cuenta")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")
            
            # Creamos el subframe para agregar la funcionalidad
            subframeFuncionalidad = Frame(cls.subframe_main,bg="#222426")
            subframeFuncionalidad.place(relheight=0.75,relwidth=1,relx=0,rely=0.25)


            # Empezamos logica de la funcionalidad
            cuentas = cls.user.comprobarConfiabilidad()
            if isinstance(cuentas,list):
                # El usuario tiene cunetas y puede realizar un prestamo
                # Le mostramos al usuario las cuentas con las que puede hacer prestamo
                cuentas = Ahorros.comprobarPrestamo(cuentas)
                if isinstance(cuentas[0],Ahorros):
                    cuentaSeleccionada=None
                    # Funciones para el funcionamiento

                    def cuentaCambiada(event):
                        def realizarPrestamo():
                            error = None
                            cantidad = cantidadFF.getValoresInsertados()[0].get()
                            try:
                                if(cantidad is None or cantidad == ""):
                                    raise genericException.NoValueInsertedException(int)
                                cantidad = int(cantidad)
                                if cantidad <=0 or cantidad>cuentaSeleccionada.getBanco().getPrestamo():
                                    raise genericException.ValuePrestamoException(cantidad,cuentaSeleccionada.getBanco().getPrestamo())
                            except ValueError:
                                error= "continuar"
                                confirmation = messagebox.askretrycancel("Mis finanzas", "Debes insertar un número. ¿Deseas intentarlo de nuevo? (Y/N): ")
                                if confirmation:
                                    error ="continuar"
                                else:
                                    error = "cancelar"

                            except genericException.ValuePrestamoException:
                                error= "continuar"
                                confirmation =messagebox.askretrycancel("Mis finanzas", genericException.ValuePrestamoException(cantidad,cuentaSeleccionada.getBanco().getPrestamo()).show_message())
                                if confirmation:
                                    error ="continuar"
                                else:
                                    error = "cancelar"

                            except genericException.NoValueInsertedException:
                                error= "continuar"
                                confirmation = messagebox.askretrycancel("Mis finanzas", genericException.NoValueInsertedException(int).show_message())
                                if confirmation:
                                    error ="continuar"

                                else:
                                    error = "cancelar"
                            print(error)
                            if error == None:
                                # Se realiza el prestamo   
                                print("todo bien")
                                prestamo =Movimientos.realizarPrestamo(cuentaSeleccionada,cantidad)
                                messagebox.showinfo("Mis Finanzas",f"¡Prestamo Creado!\n{prestamo}")
                                back_menu_main()
                                
                            elif error == "continuar":
                                pedir_prestamo()
                            else:
                                back_menu_main()
                        def cancelar():
                            back_menu_main()
                        cuentaSeleccionada = cuentas[cuentasCombobox.current()]
                        label2= Label(subframeFuncionalidad,bg="#222426",fg="white",font=fuente,text=f"El banco {cuentaSeleccionada.getBanco().getNombre()} le presta como maximo $ {cuentaSeleccionada.getBanco().getPrestamo()}. Ingrese la cantiadad que desea prestar")
                        label2.place(anchor="w",rely=0.35,relx=0.02)
                        
                        subframeCantidad = Frame(subframeFuncionalidad,bg="#222426")
                        subframeCantidad.place(relheight=0.75,relwidth=0.88,relx=0.02,rely=0.4)

                        cantidadFF = FieldFrame("Info", ["Cantidad Prestamo:"],f"Maximo ${cuentaSeleccionada.getBanco().getPrestamo()}",  frame=[subframeCantidad,1, 0, 2, 1])
                        button_continue = Button(subframeCantidad, text="Confirmar Prestamo", font=fuente, command=realizarPrestamo, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="gray", fg="white")
                        button_continue.place(rely=0.2,relwidth=0.49)
                        button_back = Button(subframeCantidad, text="Volver", font=fuente, command=cancelar, activebackground="white", activeforeground="black", cursor="cross", border=1, relief="solid", bg="white", fg="black")
                        button_back.place(relx=0.51,rely=0.2,relwidth=0.49)


                    # Le mostramos al usuario las cuentas
                    fuente = font.Font(size=12,weight="bold", family="Alegreya Sans")
                    label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text="A continuacion se le mostraran las cuentas con las que puede pedir prestamo,seleccione una para conocer la cantidad de dinero que le presta su banco:")
                    label1.place(anchor="w",rely=0.1,relx=0.02,relheight=0.1)
                    labelCuenta= Label(subframeFuncionalidad,bg="#222426",fg="white",font=fuente,text="Cuenta:")
                    labelCuenta.place(anchor="w",rely=0.25,relx=0.02)
                    
                    # Creamos selecctor
                    cuentasCombobox = Combobox(subframeFuncionalidad, cursor="cross",font=fuente)
                    cuentasCombobox["values"] = [cuentas[m].getNombre() for m in range(0, len(cuentas))]
                    cuentasCombobox['state'] = 'readonly'
                    cuentasCombobox.place(relwidth=0.75,relx=0.1,rely=0.2,relheight=0.1)
                    cuentasCombobox.bind("<<ComboboxSelected>>",cuentaCambiada)
                else:
                    messagebox.showinfo("Mis Finanzas",cuentas)
                    back_menu_main()
            else:
                messagebox.showinfo("Mis Finanzas","Usted no tiene Cuentas de ahorros,por favor intente crear una antes de solicitar un prestamo")
                back_menu_main()

        def pagar_prestamo():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Funcionalidad - Pagar Prestamo")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("Es importante pagar tus deudas para poder confiando en ti.\n En esta sección puedes pagar tus prestamos, si no has realizado ningun prestamos y quieres hacerlo ingresa a la sección Pedir Prestamo")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            # Creamos el subframe para agregar la funcionalidad
            subframeFuncionalidad = Frame(cls.subframe_main,bg="#222426")
            subframeFuncionalidad.place(relheight=0.75,relwidth=1,relx=0,rely=0.25)


            # Empezamos logica de la funcionalidad
            deudas = Deuda.conseguirDeuda(cls.user)
            if len(deudas) !=0:
                # El usuario tiene cunetas y puede realizar un prestamo
                # Le mostramos al usuario las cuentas con las que puede hacer prestamo
                deudaSeleccionada=None
                # Funciones para el funcionamiento
                def deudaCambiada(event):
                    deudaSeleccionada = deudas[deudasCombobox.current()]
                    def pagarPrestamo():
                        error = None
                        cantidad = cantidadFF.getValoresInsertados()[0].get()
                        try:
                            if(cantidad is None or cantidad == ""):
                                raise genericException.NoValueInsertedException(int)
                            cantidad = int(cantidad)
                            if cantidad <=0 or cantidad>deudaSeleccionada.getCantidad():
                                raise genericException.ValuePagarException(cantidad,deudaSeleccionada.getCantidad())
                        except ValueError:
                            error= "continuar"
                            confirmation = messagebox.askretrycancel("Mis finanzas", "Debes insertar un número. ¿Deseas intentarlo de nuevo? (Y/N): ")
                            if confirmation:
                                error ="continuar"
                            else:
                                error = "cancelar"

                        except genericException.ValuePrestamoException:
                            error= "continuar"
                            confirmation =messagebox.askretrycancel("Mis finanzas", genericException.ValuePrestamoException(cantidad,deudaSeleccionada.getCantidad()).show_message())
                            if confirmation:
                                error ="continuar"
                            else:
                                error = "cancelar"

                        except genericException.NoValueInsertedException:
                            error= "continuar"
                            confirmation = messagebox.askretrycancel("Mis finanzas", genericException.NoValueInsertedException(int).show_message())
                            if confirmation:
                                error ="continuar"

                            else:
                                error = "cancelar"
                        print(error)
                        if error == None:
                            # Se realiza el prestamo   
                            if cantidad < deudaSeleccionada.getCantidad():
                                prestamo = Movimientos.pagarDeuda(cls.user,deudaSeleccionada,int(cantidad))
                                messagebox.showinfo("Mis Finanzasa",f"Has pagado ${cantidad}. \n Su deuda ahora es de ${deudaSeleccionada.getCantidad()}")

                                pagar_prestamo()
                            else:
                                messagebox.showinfo("Mis Finanzasa",f"¡FELICIDADES!\nHas pagado por completo tu deuda")
                                prestamo = Movimientos.pagarDeuda(cls.user,deudaSeleccionada,int(cantidad))
                                pagar_prestamo()

                        elif error == "continuar":
                            pagar_prestamo()
                        else:
                            back_menu_main()


                    def cancelar():
                        back_menu_main()
                    deudaSeleccionada = deudas[deudasCombobox.current()]
                    label2= Label(subframeFuncionalidad,bg="#222426",fg="white",font=fuente,text=f"Su deuda es de $ {deudaSeleccionada.getCantidad()}. Ingrese la cantiadad que desea pagar")
                    label2.place(anchor="w",rely=0.35,relx=0.02)
                    
                    subframeCantidad = Frame(subframeFuncionalidad,bg="#222426")
                    subframeCantidad.place(relheight=0.75,relwidth=0.88,relx=0.02,rely=0.4)

                    cantidadFF = FieldFrame("Info", ["Pagar:"],f"Total ${deudaSeleccionada.getCantidad()}",  frame=[subframeCantidad,1, 0, 2, 1])
                    button_continue = Button(subframeCantidad, text="Pagar Prestamo", command=pagarPrestamo, font=fuente, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="gray", fg="white")
                    button_continue.place(rely=0.2,relwidth=0.49)
                    button_back = Button(subframeCantidad, text="Volver", font=fuente,command=back_menu_main, activebackground="white", activeforeground="black", cursor="cross", border=1, relief="solid", bg="white", fg="black")
                    button_back.place(relx=0.51,rely=0.2,relwidth=0.49)


                # Le mostramos al usuario las cuentas
                fuente = font.Font(size=12,weight="bold", family="Alegreya Sans")
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text="A continuacion se le mostraran sus deudas,seleccione la que desee pagar:")
                label1.place(anchor="w",rely=0.1,relx=0.02,relheight=0.1)
                labelCuenta= Label(subframeFuncionalidad,bg="#222426",fg="white",font=fuente,text="Cuenta:")
                labelCuenta.place(anchor="w",rely=0.25,relx=0.02)
                
                # Creamos selecctor
                deudasCombobox = Combobox(subframeFuncionalidad, cursor="cross",font=fuente)
                deudasCombobox["values"] = [f"{deudas[m].getCuenta().getNombre()} cantidad: ${deudas[m].getCantidad()}" for m in range(0, len(deudas))]
                deudasCombobox['state'] = 'readonly'
                deudasCombobox.place(relwidth=0.75,relx=0.1,rely=0.2,relheight=0.1)
                deudasCombobox.bind("<<ComboboxSelected>>",deudaCambiada)
            else:
                messagebox.showinfo("Mis Finanzasa","Usted no tiene deudas por pagar")
                back_menu_main()
        
        def verCuentas():
            # Editar la descripcion de su funcionalidad
            titulo_funcionalidad.set("Ver Cuentas")
            text_description_title.config(state="normal")
            descripcion_funcionalidad.set("En este apartado podrás ver la información de tus cuentas: ")
            text_description_title.delete("1.0", END)
            text_description_title.insert("1.0", descripcion_funcionalidad.get())
            text_description_title.tag_configure("justifying", justify="center")
            text_description_title.tag_add("justifying", "1.0", END)
            text_description_title.config(state="disabled")

            # Creamos el subframe para agregar la funcionalidad
            subframeFuncionalidad = Frame(cls.subframe_main,bg="#222426")
            subframeFuncionalidad.place(relheight=0.75,relwidth=1,relx=0,rely=0.25)

            fuente = font.Font(size=12, family="Times New Roman")
            labelNombre = Label(subframeFuncionalidad,bg="white",borderwidth=1,relief="solid",fg="black",font=fuente,text="Nombre")
            labelNombre.place(relx= 0, rely=0,relwidth=0.2, relheight=0.2)
            labelTipo = Label(subframeFuncionalidad,bg="white",borderwidth=1,relief="solid",fg="black",font=fuente,text="Tipo de Cuenta")
            labelTipo.place(relx= 0.2, rely=0,relwidth=0.2, relheight=0.2)
            labelDivisa = Label(subframeFuncionalidad,bg="white",borderwidth=1,relief="solid",fg="black",font=fuente,text="Nombre")
            labelDivisa.place(relx= 0.4, rely=0,relwidth=0.2, relheight=0.2)
            labelBanco = Label(subframeFuncionalidad,bg="white",borderwidth=1,relief="solid",fg="black",font=fuente,text="Banco")
            labelBanco.place(relx= 0.6, rely=0,relwidth=0.2, relheight=0.2)
            labelSaldo = Label(subframeFuncionalidad,bg="white",borderwidth=1,relief="solid",fg="black",font=fuente,text="Saldo/Disponible")
            labelSaldo.place(relx= 0.8, rely=0,relwidth=0.2, relheight=0.2)

            # Nombre
            i=0
            while i< len(cls.user.getCuentasAsociadas()):
                cuenta = cls.user.getCuentasAsociadas()[i]
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text=cuenta.getNombre())
                label1.place(relx=0, rely=0.2+(i)*0.1,relwidth=0.2, relheight=0.1)
                i+=1
            # Tipo
            i=0
            while i< len(cls.user.getCuentasAsociadas()):
                cuenta = cls.user.getCuentasAsociadas()[i]
                if isinstance(cuenta,Ahorros):
                    tipo= "Ahorros"
                else:
                    tipo= "Corriente"
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text=tipo)
                label1.place(relx=0.2, rely=0.2+(i)*0.1,relwidth=0.2, relheight=0.1)
                i+=1
            # Divisa 
            i=0
            while i< len(cls.user.getCuentasAsociadas()):
                cuenta = cls.user.getCuentasAsociadas()[i]
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text=cuenta.getDivisa().value)
                label1.place(relx=0.4, rely=0.2+(i)*0.1,relwidth=0.2, relheight=0.1)
                i+=1
            # Banco 
            i=0
            while i< len(cls.user.getCuentasAsociadas()):
                cuenta = cls.user.getCuentasAsociadas()[i]
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text=cuenta.getBanco().getNombre())
                label1.place(relx=0.6, rely=0.2+(i)*0.1,relwidth=0.2, relheight=0.1)
                i+=1
            # Saldo
            i=0
            while i < len(cls.user.getCuentasAsociadas()):
                cuenta = cls.user.getCuentasAsociadas()[i]
                if isinstance(cuenta,Ahorros):
                    saldo = cuenta.getSaldo()
                else:
                    saldo = cuenta.getDisponible()
                label1= Label(subframeFuncionalidad,bg="gray",borderwidth=1,relief="solid",fg="white",font=fuente,text=str(saldo))
                label1.place(relx=0.8, rely=0.2+(i)*0.1,relwidth=0.2, relheight=0.1)
                i+=1
            button_back_main = Button(subframeFuncionalidad, text="Volver al menú principal", font=fuente, command=back_menu_main, activebackground="gray", activeforeground="black", cursor="cross", border=1, relief="solid", bg="#8C7566", fg="white")
            button_back_main.place(relx=0, rely=0.2+(len(cls.user.getCuentasAsociadas()))*0.1, relwidth=1, relheight=0.15)  
        
        # Configuración básica de parámetros de la ventana Principal
        cls.main_window = Tk()
        cls.main_window.geometry("1100x800")
        cls.main_window.title("Mis Finanzas")
        #cls.main_window.resizable(0, 0)
        route_logo = os.path.join(cls.current_directory + "\static", "logo.png")
        logo = PhotoImage(file=route_logo)
        cls.main_window.iconphoto(True, logo)

        # Configuración básica de los parámetros del main_frame en la ventana Principal
        main_frame = Frame(cls.main_window, bg="#DFDEDE")
        main_frame.pack(fill="both", expand=True)
        titulo_funcionalidad = StringVar(main_frame, value="Bienvenido " + cls.user.getNombre() + " a Mis Finanzas") 

        # Configuración de los subs-frames

        # subframe del titulo
        subframe_title = Frame(main_frame, bg="black",
                             borderwidth=1, relief="solid")
        subframe_title.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)
        cls.subframe_main = Frame(
            main_frame, bg="#DFDEDE", borderwidth=1, relief="solid", name="subframe_main")
        cls.subframe_main.place(
            relheight=0.85, relwidth=0.94, rely=0.15, relx=0.03)

        # Opciones dentro del menú procesos y consultas
        proceso1 = "Modificar mi suscripcion"
        proceso2 = "Invertir saldo de mi cuenta"
        proceso3 = "Consignar saldo a mi cuenta"
        proceso4 = "Transferir saldo entre cuentas"
        proceso5 = "Compra con cuenta corriente"
        proceso6 = "Gestionar mis prestamos"
        proceso7 = "Asesoramiento de inversiones"
        proceso8 = "Compra de cartera"
        proceso9 = "Calculadora financiera"

        # Configuración de barra de menú para ventana principal
        home_menu = Menu(subframe_title, cursor="cross")
        
        # Configuración del menú archivo
        archivo = Menu(home_menu, tearoff=0)
        archivo.add_command(label="Aplicación",
                            activebackground="gray", activeforeground="white", command=show_description)
        archivo.add_command(label="Cerrar sesión", command=exit_principal_window,
                            activebackground="gray", activeforeground="white")

        # Configuración del menú de ayuda
        ayuda_menu = Menu(home_menu, tearoff=0)
        ayuda_menu.add_command(label="Acerca de", command=about,
                             activebackground="gray", activeforeground="white")

        # Configuración del menú de Procesos y Consultas
        procesos_consultas = Menu(home_menu, tearoff=0)

        # Agregamos los submenús al gestionar prestamos.
        prestamos_menu = Menu(procesos_consultas, tearoff=0)
        prestamos_menu.add_command(label="Pedir prestamos", command=pedir_prestamo,
                                   activebackground="gray", activeforeground="white")
        prestamos_menu.add_command(label="Pagar prestamos", command=pagar_prestamo,
                                   activebackground="gray", activeforeground="white")

        # Agregamos los submenús a la barra de menú.
        procesos_consultas.add_command(label="Volver al menú principal", command=back_menu_main, activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label="Crear una cuenta", command=create_account_user, activebackground="gray", activeforeground="white")
        procesos_consultas.add_command(label="Ver mis cuentas", command=verCuentas, activebackground="gray", activeforeground="white")

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
        label_title = Label(subframe_title, textvariable=titulo_funcionalidad, fg="black",
                               bg="#94B43B", wraplength=400, font=title_Font_Style, width=50)
        label_title.place(anchor="w", relheight=0.97,
                          relwidth=0.8945, rely=0.5, relx=0.001)
        
        # -------Imagen del titulo(subframe_title)
        route_image = os.path.join(cls.current_directory + "\static", "unal.png")
        upper_image = PhotoImage(file=route_image)
        upper_image = upper_image.subsample(4)
        upper_image_label = Label(subframe_title, image=upper_image)
        upper_image_label.place(
            anchor="e", relheight=0.97, relwidth=0.101, rely=0.5, relx=0.999)

        # ------------Descripcion de la funcionalidad
        subframe_description_title = Frame(
            cls.subframe_main, bg="gray", borderwidth=1, relief="solid", name="subframe_description_title")
        subframe_description_title.place(
            relheight=0.25, relwidth=1, rely=0.0, relx=0.0)
        descripcion_font_style = font.Font(size=13, family="Alegreya Sans", slant="italic")
        descripcion_funcionalidad = StringVar(main_frame)
        text_description_title = Text(subframe_description_title, cursor="cross", fg="white", bg="#565A5C", font=descripcion_font_style, wrap="word", spacing1=8, border=1, relief="groove")
        text_description_title.pack(fill="both", expand=True)
        # --------------------------------------------------
        # ------------Nota sobre la zona de componentes
        subframe_description_component = Frame(
            cls.subframe_main, bg="#DFDEDE", borderwidth=1, relief="solid", name="subframe_description_component")
        subframe_description_component.place(
            relheight=0.5, relwidth=1, rely=0.25, relx=0.0)
        basic_instructions = StringVar(main_frame)
        text_description_component = Text(subframe_description_component, cursor="cross", fg="black", bg="#B1B2B0", font=(
            "Alegreya Sans", 13), wrap="word", spacing1=8, border=1, relief="groove")
        text_description_component.pack(fill="both", expand=True, anchor="center")
        welcome_text_reset()
    # --------------------------------------------------
        cls.main_window.mainloop()

    @classmethod
    def getMainWindow(cls):
        return cls.main_window
    
    @classmethod
    def getInitialWindow(cls):
        return cls.initial_window
    
    @classmethod
    def getSubframeMain(cls):
        return cls.subframe_main
# --------------------------------------------------

if __name__ == "__main__":
    # Poner código para ejecutar la interfaz
    App().start_initial_window()
