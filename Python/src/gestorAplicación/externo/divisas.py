from enum import Enum
class Divisas(Enum):
    EUR ="EUR"
    USD ="USD"
    COP="COP"
    
    def getDivisas():
        return [Divisas.EUR.value,Divisas.USD.value,Divisas.COP.value]
    
