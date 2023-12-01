import random
from time import time
from datetime import date, datetime
import haravasto as h

pelin_alku_aika = 0
avatut = []
kentta = []
liput = []
peliloppu = False


def piirra_kentta_kasittelija():
    # toimii käsittelijäfunktiona ja kutsutaan haravastosta. Piirtää kentän.
    h.tyhjaa_ikkuna()
    h.piirra_tausta()
    h.aloita_ruutujen_piirto()
    for y in range(len(kentta)):
        for x in range(len(kentta[0])):
            key = "{}-{}".format(x, y)
            if key in avatut:
                h.lisaa_piirrettava_ruutu(kentta[y][x], x * 40, y * 40)
            elif key in liput:
                h.lisaa_piirrettava_ruutu("f", x * 40, y * 40)
            else:
                h.lisaa_piirrettava_ruutu(" ", x * 40, y * 40)
    h.piirra_ruudut()


def hae_vapaat_ruudut():
    vapaat = []
    for y in range(len(kentta)):
        for x in range(len(kentta[0])):
            if kentta[y][x] == " ":
                vapaat.append((x, y))

    return vapaat


def miinoita(vapaat_ruudut, miinat):
    for miinanro in range(miinat):
        indexi = random.randint(0, len(vapaat_ruudut) - 1)
        ruutu = vapaat_ruudut[indexi]
        x = ruutu[0]
        y = ruutu[1]
        kentta[y][x] = "x"
        vapaat_ruudut.pop(indexi)


def aseta_numero(x, y):
    numerolkm = 0
    for rivinro, rivi in enumerate(kentta):
        if rivinro >= y - 1 and rivinro <= y + 1:
            for sarakenro, merkki in enumerate(kentta[rivinro]):
                if sarakenro >= x - 1 and sarakenro <= x + 1:
                    if merkki == "x":
                        numerolkm += 1
    kentta[y][x] = "{}".format(numerolkm)


def aseta_numerot():
    for rivinro, rivi in enumerate(kentta):
        for sarakenro, sarake in enumerate(kentta[0]):
            if kentta[rivinro][sarakenro] == " ":
                aseta_numero(sarakenro, rivinro)


def tulvataytto(x, y):
    tyhjat_ruudut = [(x, y)]
    while len(tyhjat_ruudut) > 0:
        tyhja_ruutu = tyhjat_ruudut.pop()
        xkoord = tyhja_ruutu[0]
        ykoord = tyhja_ruutu[1]
        # lisaa_avattu on funktio joka lisaa klikatun ruudun avatut listaan
        lisaa_avattu(xkoord, ykoord)

        for rivinro, rivi in enumerate(kentta):
            if rivinro >= ykoord - 1 and rivinro <= ykoord + 1:
                for sarakenro, merkki in enumerate(rivi):
                    if sarakenro >= xkoord - 1 and sarakenro <= xkoord + 1:
                        if merkki == "0":
                            if not onko_avattu(sarakenro, rivinro):
                                tyhjat_ruudut.append((sarakenro, rivinro))
                        if merkki != "x":
                            lisaa_avattu(sarakenro, rivinro)


def onko_avattu(x, y):
    key = "{}-{}".format(x, y)
    return key in avatut


def kasittele_ruutu(x, y):
    if kentta[y][x] == "x":
        # avaa loputkin miinat
        for y in range(len(kentta)):
            for x in range(len(kentta[0])):
                if kentta[y][x] == "x":
                    lisaa_avattu(x, y)
        pelin_loppu(False)
    elif kentta[y][x] == "0":

        tulvataytto(x, y)

    else:
        lisaa_avattu(x, y)


def lisaa_avattu(x, y):
    key = "{}-{}".format(x, y)

    if key not in avatut:
        avatut.append(key)
        # If lause tarkistaa onko kaikki ruudut avattuja.
        if len(avatut) == len(kentta) * len(kentta[0]) - miinalkm:
            pelin_loppu(True)


def hiiri_kasittelija(x_sijainti, y_sijainti, hiiren_painike, muokkausnappain):
    if peliloppu:
        return
    x_sijainti = x_sijainti // 40
    y_sijainti = y_sijainti // 40
    if hiiren_painike == h.HIIRI_VASEN:
        kasittele_ruutu(x_sijainti, y_sijainti)
    elif hiiren_painike == h.HIIRI_OIKEA:
        if not onko_avattu(x_sijainti, y_sijainti):
            key = "{}-{}".format(x_sijainti, y_sijainti)
            if key not in liput:
                liput.append(key)
            else:
                liput.remove(key)


def pelin_loppu(voitto):

    if voitto:
        print("Voitit pelin!")
        with open("tulokset.txt", "a") as kohde:
            kohde.write("\n Voitit pelin!")
    else:
        print("Astuit miinaan!")
        with open("tulokset.txt", "a") as kohde:
            kohde.write("\nHavisit pelin!")
    global peliloppu
    peliloppu = True
    pelin_kesto = int(time()) - pelin_alku_aika
    pelin_kesto_minuutteina = pelin_kesto // 60
    with open("tulokset.txt", "a") as kohde:
        kohde.write(
            "\n {}, {}, Peli kesti: {} minuuttia".format(
                date.today(), datetime.now().strftime("%H:%M"), pelin_kesto_minuutteina
            )
        )


def main():
    """
    Lataa pelin grafiikat, luo peli-ikkunan ja asettaa siihen piirtokäsittelijän.
    """
    print("Hei!")
    print("Valitse jokin vaihtoehdoista 1 - 3")
    print("Jos valitset jotain muuta, piirretään 9x9 kenttä kymmenellä miinalla")
    print("1 - uusi peli")
    print("2 - Lopeta peli")
    print("3 - Katso tilastoja")
    valinnat = ["1", "2", "3"]
    global miinalkm
    valinta = input(": ")

    if valinta == "1":
        while True:
            try:
                korkeus = int(input("Anna korkeus: "))
                if korkeus < 3:
                    print("Liian pieni korkeus!")

                elif korkeus > 32:
                    print("Liian suuri korkeus!")
                else:
                    break

            except ValueError:
                print("Anna kokonaisluku!")

        while True:
            try:
                leveys = int(input("Anna leveys: "))
                if leveys < 3:
                    print("Liian pieni leveys!")
                elif leveys > 32:
                    print("Liian suuri leveys!")
                else:
                    break
            except ValueError:
                print("Anna kokonaisluku!")

        while True:
            try:
                miinalkm = int(input("Anna miinojen lukumäärä: "))
                if miinalkm >= korkeus * leveys:
                    print("Liikaa miinoja!")
                elif miinalkm <= 0:
                    print("Liian vähän miinoja!")
                else:
                    break
            except ValueError:
                print("Anna kokonaisluku!")

    if valinta == "2":
        return
    if valinta == "3":
        f = open("tulokset.txt", "r")
        print(f.read())
        return

    if valinta not in valinnat:
        korkeus = 9
        leveys = 9
        miinalkm = 10

    for rivi in range(korkeus):
        kentta.append([])
        for sarake in range(leveys):
            kentta[-1].append(" ")

    hae_vapaat_ruudut()
    miinoita(hae_vapaat_ruudut(), miinalkm)
    aseta_numerot()
    h.lataa_kuvat("spritet")
    h.luo_ikkuna(len(kentta[0]) * 40, len(kentta) * 40)
    h.aseta_piirto_kasittelija(piirra_kentta_kasittelija)
    h.aseta_hiiri_kasittelija(hiiri_kasittelija)
    global pelin_alku_aika
    pelin_alku_aika = int(time())
    h.aloita()


main()
