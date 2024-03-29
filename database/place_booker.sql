PGDMP     3                     {           place_booker    15.3    15.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24577    place_booker    DATABASE     �   CREATE DATABASE place_booker WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE place_booker;
                postgres    false            �            1259    24630    booking    TABLE     \  CREATE TABLE public.booking (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    guest_number integer NOT NULL,
    booked_from date NOT NULL,
    booked_to date NOT NULL,
    user_id bigint NOT NULL,
    place_id bigint NOT NULL
);
    DROP TABLE public.booking;
       public         heap    postgres    false            �            1259    24633    booking_id_seq    SEQUENCE     �   CREATE SEQUENCE public.booking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.booking_id_seq;
       public          postgres    false    218                       0    0    booking_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.booking_id_seq OWNED BY public.booking.id;
          public          postgres    false    219            �            1259    24598    place    TABLE     3  CREATE TABLE public.place (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(255),
    image_url character varying(255),
    price double precision NOT NULL,
    available_from date NOT NULL,
    available_to date NOT NULL,
    user_id bigint NOT NULL
);
    DROP TABLE public.place;
       public         heap    postgres    false            �            1259    24597    place_id_seq    SEQUENCE     �   CREATE SEQUENCE public.place_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.place_id_seq;
       public          postgres    false    217                       0    0    place_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.place_id_seq OWNED BY public.place.id;
          public          postgres    false    216            �            1259    24841    place_location    TABLE       CREATE TABLE public.place_location (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    lat character varying(255) NOT NULL,
    lng character varying(255) NOT NULL,
    static_map_image_url character varying(255) NOT NULL,
    place_id bigint NOT NULL
);
 "   DROP TABLE public.place_location;
       public         heap    postgres    false            �            1259    24840    place_location_id_seq    SEQUENCE     �   CREATE SEQUENCE public.place_location_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.place_location_id_seq;
       public          postgres    false    221                        0    0    place_location_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.place_location_id_seq OWNED BY public.place_location.id;
          public          postgres    false    220            �            1259    24579    user    TABLE     4  CREATE TABLE public."user" (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    age integer NOT NULL,
    CONSTRAINT "UN_age" CHECK ((age > 0))
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    24578    user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.user_id_seq;
       public          postgres    false    215            !           0    0    user_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;
          public          postgres    false    214            v           2604    24678 
   booking id    DEFAULT     h   ALTER TABLE ONLY public.booking ALTER COLUMN id SET DEFAULT nextval('public.booking_id_seq'::regclass);
 9   ALTER TABLE public.booking ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218            u           2604    24727    place id    DEFAULT     d   ALTER TABLE ONLY public.place ALTER COLUMN id SET DEFAULT nextval('public.place_id_seq'::regclass);
 7   ALTER TABLE public.place ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            w           2604    24854    place_location id    DEFAULT     v   ALTER TABLE ONLY public.place_location ALTER COLUMN id SET DEFAULT nextval('public.place_location_id_seq'::regclass);
 @   ALTER TABLE public.place_location ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    221    221            t           2604    24801    user id    DEFAULT     d   ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);
 8   ALTER TABLE public."user" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    214    215            {           2606    24803 
   user PK_id 
   CONSTRAINT     L   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "PK_id" PRIMARY KEY (id);
 8   ALTER TABLE ONLY public."user" DROP CONSTRAINT "PK_id";
       public            postgres    false    215                       2606    24729    place PK_place_id 
   CONSTRAINT     Q   ALTER TABLE ONLY public.place
    ADD CONSTRAINT "PK_place_id" PRIMARY KEY (id);
 =   ALTER TABLE ONLY public.place DROP CONSTRAINT "PK_place_id";
       public            postgres    false    217            y           2606    24652    booking UN_guest_number    CHECK CONSTRAINT     f   ALTER TABLE public.booking
    ADD CONSTRAINT "UN_guest_number" CHECK ((guest_number > 0)) NOT VALID;
 >   ALTER TABLE public.booking DROP CONSTRAINT "UN_guest_number";
       public          postgres    false    218    218            }           2606    24589    user UQ_email 
   CONSTRAINT     M   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "UQ_email" UNIQUE (email);
 ;   ALTER TABLE ONLY public."user" DROP CONSTRAINT "UQ_email";
       public            postgres    false    215            �           2606    24875 &   place_location UQ_place_location_place 
   CONSTRAINT     k   ALTER TABLE ONLY public.place_location
    ADD CONSTRAINT "UQ_place_location_place" UNIQUE (id, place_id);
 R   ALTER TABLE ONLY public.place_location DROP CONSTRAINT "UQ_place_location_place";
       public            postgres    false    221    221            �           2606    24680    booking booking_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.booking DROP CONSTRAINT booking_pkey;
       public            postgres    false    218            �           2606    24856 "   place_location place_location_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.place_location
    ADD CONSTRAINT place_location_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.place_location DROP CONSTRAINT place_location_pkey;
       public            postgres    false    221            �           2606    24735    booking FK_booking_place    FK CONSTRAINT     �   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT "FK_booking_place" FOREIGN KEY (place_id) REFERENCES public.place(id) ON UPDATE CASCADE ON DELETE RESTRICT NOT VALID;
 D   ALTER TABLE ONLY public.booking DROP CONSTRAINT "FK_booking_place";
       public          postgres    false    3199    217    218            �           2606    24804    booking FK_booking_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.booking
    ADD CONSTRAINT "FK_booking_user" FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 C   ALTER TABLE ONLY public.booking DROP CONSTRAINT "FK_booking_user";
       public          postgres    false    215    218    3195            �           2606    24863 &   place_location FK_place_location_place    FK CONSTRAINT     �   ALTER TABLE ONLY public.place_location
    ADD CONSTRAINT "FK_place_location_place" FOREIGN KEY (place_id) REFERENCES public.place(id) ON UPDATE CASCADE ON DELETE CASCADE;
 R   ALTER TABLE ONLY public.place_location DROP CONSTRAINT "FK_place_location_place";
       public          postgres    false    3199    217    221            �           2606    24809    place FK_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.place
    ADD CONSTRAINT "FK_user" FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 9   ALTER TABLE ONLY public.place DROP CONSTRAINT "FK_user";
       public          postgres    false    3195    217    215           