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

# FAVOR SER ORDENADOS CON EL CÓDIGO Y COMENTAR TODO BIEN. USAR SNAKECASE. NOMBRAR VARIABLES Y MÉTODOS EN INGLÉS

class App():

    # Guardar objetos al sistema
    """ user1 = Usuario(_nombre="pepe", _correo="pepe@mail", _contrasena="123", _suscripcion=Suscripcion.BRONCE)
    Serializador.serializar([user1]) """
    #user1 = Usuario(_nombre="Jaime Guzman", _correo="JaimeGuzman@mail", _contrasena="12345", _suscripcion=Suscripcion.BRONCE)
    #Serializador.serializar([user1])

    # Cargar objetos al sistema
    Deserializador.deserializar("Usuarios")

    # Variables de clase para funcionamiento de la app
    initial_window = None
    main_window = None
    user = None
    image_index = 0  # Variable para realizar un seguimiento del índice del pack de imagenes de los desarrolladores

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
        cls.initial_window.resizable(0, 0)
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
                                 font=style, text="Ingresar", activebackground="gray", activeforeground="black")
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

        # Metodos de las funcionalidades del menú
        def comprobar_suscripcion():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Modificar Suscripcion")
            label_description.config(text="(REVISAR)El método de instancia comprobarSuscripcion que se encuentra en la clase Banco tiene como parámetro una instancia de la clase Usuario. En este método se consulta el atributo Suscripcion de la instancia de Usuario dada por parámetro y, con base en este, se modifica el atributo de instancia limiteCuentas de tipo int de la misma instancia de Usuario. Este atributo limiteCuentas se utiliza para establecer la cantidad de instancias diferentes de la clase Cuenta que se le pueden asociar a través del método de instancia asociarCuentas, que se encuentra dentro de la clase Usuario, a la misma instancia de Usuario pasada por parámetro. Estas cuentas son añadidas al atributo de instancia cuentasAsociadas de tipo list, que se encuentra dentro de la clase Usuario. El atributo comision se invoca haciendo uso del self, luego, este valor se multiplica por K, donde K es un factor que varía con base en el atributo suscripcion del Usuario pasado por parámetro en el método.")

        def invertir_saldo():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Invertir Saldo")
            label_description.config(
                text="(REVISAR)El método de instancia invertirSaldo que se encuentra en la clase Ahorros consulta el atributo de instancia titular de tipo Usuario, de la instancia de Ahorros utilizada para ejecutar el método, usando el operador self y el método de instancia getTitular, posteriormente, verifica el atributo de instancia suscripcion de la instancia titular y obtiene la constante probabilidad_Inversion de tipo float asociada a este. Esta última constante se utiliza para realizar un cálculo aritmético que se almacena dentro de una variable de tipo double llamada rand y se evalúa que rand sea mayor ó igual a uno. Posteriormente, si la condición es true: se realiza un Movimiento ó si la condición es false: retorna un String.")

        def consignar_saldo():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Consignar Saldo")
            label_description.config(
                text="(REVISAR)El método estático crearMovimiento que se encuentra en la clase Movimientos recibe como parámetros una instancia de Ahorros, un enum de Categoria, un dato de tipo double llamado saldo_consignar y un objeto de tipo date. Este método consulta el atributo de clase cuentasTotales de tipo list de la clase Cuenta, posteriormente se crea una instancia de la clase Movimientos que se asocia a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario, finalmente, se retorna la instancia de Movimientos.")

        def transferir_saldo():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Transferir Saldo")
            label_description.config(
                text="(REVISAR)El método estático modificarSaldo que se encuentra en la clase Movimientos recibe como parámetros una instancia de Usuario, dos instancias de Ahorros, un enum de Categoria y un dato de tipo double llamado cantidad. Este método consulta el atributo de instancia cuentasAsociadas de tipo list de la instancia de Usuario pasada por parámetro, posteriormente comprueba que el atributo de instancia llamado origen de tipo Ahorros pasado por parámetro se encuentre dentro de la lista cuentasAsociadas. Posteriormente se llama al método crearMovimiento de la clase Movimientos y éste último es asociado a la instancia de Usuario pasada por parámetro usando el método de instancia asociarMovimiento de la clase Usuario, finalmente, se retorna la instancia de Movimientos.")

        def compra_corriente(cuenta = None):
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Comprar con cuenta corriente")
            label_description.config(
                text="Agregar la descripcion en el metodo compra_corriente y agregar aca el funcionamiento de su funcionalidad")

            #Desarrollo de la funcionalidad
            if cuenta == None:
                #Arreglo que almacena las cuentas con deuda alguna
                cuentasEnDeuda = cls.user.retornarDeudas()
                cuentasEnDeuda.sort()

                #Arreglo que almacena las cuentas asociadas a un usuario
                cuentasAux = cls.user.getCuentasAsociadas()
                cuentasAux.sort()

                cuentasAux1 = cls.user.getCuentasCorrienteAsociadas()

                #Comprobación de existencia de Cuentas Corriente por parte del Usuario
                if len(cuentasAux1) <= 1:
                    print ("Falta revisar que se hace con esto")
                    return
                
                if len(cuentasEnDeuda) == 0:
                    print("Revisar que pasa con esto")
                    return
                
                cuenta_Compra = 0
                seleccion_Cuenta = False

                while seleccion_Cuenta:
                    print("Verificar impresión")


            
            else:
                pass


        def asesoramiento_inversiones():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Asesoramiento de Inversiones")
            label_description.config(
                text="Agregar la descripcion en el metodo asesoramiento_inversiones y agregar aca el funcionamiento de su funcionalidad")

        def compra_cartera():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Compra Catera")
            label_description.config(
                text="Agregar la descripcion en el metodo compra_cartera y agregar aca el funcionamiento de su funcionalidad")

        def calculadora_financiera():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Calculadora Financiera")
            label_description.config(
                text="Agregar la descripcion en el metodo calculadora_financiera y agregar aca el funcionamiento de su funcionalidad")

        def pedir_prestamo():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Pedir Prestamo")
            label_description.config(
                text="¿Necesitas dinero? realiza un prestamo con tu banco.\nPara pedir un prestamo es necesario que cuentes con una cuenta de ahorros, la cantidad de dinero que puedes prestar va a depender de tu nivel de suscripción y del banco asociado a tu cuenta")

        def pagar_prestamo():
            # Editar la descripcion de su funcionalidad
            label_title.config(text="Funcionalidad - Pagar Prestamo")
            label_description.config(
                text="Es importante pagar tus deudas para poder confiando en ti.\n En esta sección puedes pagar tus prestamos, si no has realizado ningun prestamos y quieres hacerlo ingresa a la sección Pedir Prestamo")

        # Configuración básica de parámetros de la ventana Principal
        cls.main_window = tk.Tk()
        cls.main_window.geometry("1000x800")
        cls.main_window.title("Mis Finanzas")
        cls.main_window.resizable(0, 0)
        current_directory = os.path.dirname(os.path.abspath(__file__))
        route_logo = os.path.join(current_directory + "\static", "logo.png")
        logo = tk.PhotoImage(file=route_logo)
        cls.main_window.iconphoto(True, logo)

        tituloFuncionalidad = "Bienvenido " + cls.user.getNombre() + " a Mis Finanzas"
        # Configuración básica de los parámetros del main_frame en la ventana Principal
        main_frame = tk.Frame(cls.main_window, bg="#DFDEDE")
        main_frame.pack(fill="both", expand=True)

        # Configuración de los subs-frames

        # subframe del titulo
        subframe_title = tk.Frame(main_frame, bg="black",
                             borderwidth=1, relief="solid")
        subframe_title.place(anchor="nw", relwidth=0.94, relheight=0.1, relx=0.03)

        subframe_main = tk.Frame(
            main_frame, bg="white", borderwidth=1, relief="solid")
        subframe_main.place(
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
                            activebackground="gray", activeforeground="white")
        archivo.add_command(label="Cerrar sesión", command=exit_principal_window,
                            activebackground="gray", activeforeground="white")

        # Configuración del menú procesos y consultas
        procesos = tk.Menu(home_menu, tearoff=0)

        # Agregamos los submenús al gestionar prestamos.
        prestamos_menu = tk.Menu(procesos, tearoff=0)
        prestamos_menu.add_command(label="Pedir Prestamos", command=pedir_prestamo,
                                   activebackground="gray", activeforeground="white")
        prestamos_menu.add_command(label="Pagar Prestamos", command=pagar_prestamo,
                                   activebackground="gray", activeforeground="white")
        
        # Agregamos los submenús al menú procesos y consultas.
        procesos.add_command(label=proceso1, command=comprobar_suscripcion,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso2, command=invertir_saldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso3, command=consignar_saldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso4, command=transferir_saldo,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso5, command=compra_corriente,
                             activebackground="gray", activeforeground="white")
        procesos.add_cascade(label=proceso6, menu=prestamos_menu,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso7, command=asesoramiento_inversiones,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso8, command=compra_cartera,
                             activebackground="gray", activeforeground="white")
        procesos.add_command(label=proceso9, command=calculadora_financiera,
                             activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Archivo", menu=archivo,
                              activebackground="gray", activeforeground="white")
        home_menu.add_cascade(label="Procesos y Consultas", menu=procesos,
                              activebackground="gray", activeforeground="white")

        #Añadir la barra de menú a la ventana principal
        cls.main_window.config(menu=home_menu)

        # -------Texto de título(subframe_title)---------------------
        title_Font_Style = font.Font(
            weight="bold", size=12, family="Alegreya Sans")
        label_title = tk.Label(subframe_title, text=tituloFuncionalidad, fg="white",
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
            subframe_main, bg="gray", borderwidth=1, relief="solid")
        subframe_description.place(
            relheight=0.25, relwidth=1, rely=0.0, relx=0.0)
        descripcion_font_style = font.Font(size=12, family="Alegreya Sans")
        descripcion_funcionalidad = "Ad cillum enim occaecat aliqua ad ad sit. Reprehenderit laboris elit veniam minim esse elit. Anim deserunt officia irure proident non velit duis sint quis aute Lorem id."
        label_description = tk.Label(subframe_description, text=descripcion_funcionalidad,
                                    fg="white", bg="gray", font=descripcion_font_style, wraplength=800)
        label_description.pack(fill="both", expand=True)

        cls.main_window.mainloop()
    # --------------------------------------------------

    # ----------------- LÓGICA DEL MAIN --------------


    # --------------------------------------------------

if __name__ == "__main__":
    # Poner código para ejecutar la interfaz
    App().start_initial_window()
