class NoValueInsertedException(Exception):
    def __init__(self, _type, *args: object) -> None:
        super().__init__(*args)
        self._type = _type

    def show_message(self):
        return("Error. Se esperaba la inserción de un valor de tipo {}. \n¿Desea intentarlo de nuevo? ".format(self._type.__name__))