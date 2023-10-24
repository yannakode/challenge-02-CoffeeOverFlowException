CREATE TABLE products_tb (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL UNIQUE,
                             value DECIMAL(10, 2) NOT NULL,
                             description VARCHAR(255) NOT NULL,
                             CONSTRAINT check_description_min_length CHECK (CHAR_LENGTH(description) >= 10),
                             CONSTRAINT check_value_positive CHECK (value > 0)
);

INSERT IGNORE INTO products_tb (name, value, description)
VALUES
    ('SmartGlasses ProView X2', 499.99, 'Óculos inteligentes com realidade aumentada.'),
    ('HealthGuard BioTracker', 89.99, 'Monitor de saúde com rastreamento de batimentos cardíacos e oxigênio no sangue.'),
    ('NeuroSense Brainwave Enhancer', 349.99, 'Dispositivo de biofeedback para melhorar a concentração e reduzir o estresse.'),
    ('AirFit Respiratory Assistant', 129.99, 'Máscara facial inteligente para rastreamento da qualidade do ar e sono.'),
    ('HoloFit VR Training Suit', 799.99, 'Traje de treinamento de realidade virtual para exercícios imersivos.'),
    ('TranslateWear Language Buds', 69.99, 'Fones de ouvido sem fio com tradução em tempo real.'),
    ('BioSync Emotion Tracker', 119.99, 'Pulseira que monitora emoções e fornece feedback emocional.'),
    ('FashionSense Style Advisor', 149.99, 'Colar inteligente com recomendações de moda personalizadas.'),
    ('SoundHug Ambient Audio Scarf', 59.99, 'Cachecol com alto-falantes embutidos e conectividade Bluetooth.'),
    ('SleepScape Dream Helmet', 249.99, 'Capacete de sono com terapia de luz e som para indução de sonhos vívidos.');
