import React, { useState, useEffect } from 'react';

const OptimizedImage = ({ 
  src, 
  alt, 
  width, 
  height, 
  className, 
  style = {},
  loading = "lazy",
  onLoad,
  isMobile,
  srcSet,      // Nuevo prop
  sizes        // Nuevo prop
}) => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(false);
  const [imageSrc, setImageSrc] = useState('');

  useEffect(() => {
    if (srcSet) {
      setImageSrc(src.startsWith('/') ? src : `/${src}`);
      return;
    }
    const normalizedSrc = src.startsWith('/') ? src : `/${src}`;
    const basePath = normalizedSrc.substring(0, normalizedSrc.lastIndexOf('.'));
    const extension = normalizedSrc.split('.').pop();

    // Usa exactamente los tamaños que tienes disponibles
    const targetWidth = isMobile ? 100 : 200;

    const optimizedSrc = `${basePath}-${targetWidth}x${targetWidth}.${extension}`;
    setImageSrc(optimizedSrc);
  }, [src, isMobile]);

  const handleLoad = () => {
    setIsLoaded(true);
    if (onLoad) onLoad();
  };

  const handleError = () => {
    setError(true);
    // Si falla la imagen optimizada, intentar con la original
    if (imageSrc !== src) {
      setImageSrc(src);
      setError(false);
    } else if (imageSrc.endsWith('.webp')) {
      // Si falla .webp, intentar con .png
      setImageSrc(imageSrc.replace('.webp'));
      setError(false);
    } else {
      console.warn(`Error loading image: ${src}`);
    }
  };

  const containerStyle = {
    width,
    height,
    position: 'relative',
    overflow: 'hidden',
    ...(className ? {} : {})
  };

  const imageStyle = {
    opacity: isLoaded ? 1 : 0,
    transition: 'opacity 0.3s ease-in-out',
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    ...style
  };

  return (
    <div style={containerStyle} className={className}>
      {!isLoaded && !error && (
        <div style={{
          position: 'absolute',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: '#f0f0f0',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center'
        }}>
          <div className="loading-spinner" />
        </div>
      )}
      
      <picture>
        {srcSet ? (
          <>
            <source type="image/webp" srcSet={srcSet.webp} sizes={sizes} />
            <img
              src={src}
              srcSet={srcSet.webp}
              sizes={sizes}
              alt={alt}
              width={width}
              height={height}
              loading={loading}
              onLoad={handleLoad}
              onError={handleError}
              style={imageStyle}
              decoding="async"
            />
          </>
        ) : (
          <>
            <source type="image/webp" srcSet={imageSrc} />
            <source type="image/png" srcSet={imageSrc.replace('.webp')} />
            <img
              src={imageSrc}
              alt={alt}
              width={width}
              height={height}
              loading={loading}
              onLoad={handleLoad}
              onError={handleError}
              style={imageStyle}
              decoding="async"
            />
          </>
        )}
      </picture>
    </div>
  );
};

export default OptimizedImage;