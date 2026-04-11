package com.seguridadlimite.models.aprendiz.application.extraerfirma;

import lombok.extern.slf4j.Slf4j;

import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@AllArgsConstructor
@Slf4j
public class ExtraerFirmaServiceImpl implements IExtraerFirmaService {
	private GetPathFiles getPathFiles;

	public String extraerFirma(Long id) throws BusinessException {
		String fileName = getPathFiles.getSignaturesPath() + "S" + id + ".png";

		File fileFirma = new File(fileName);

		if (fileFirma.exists()) {
			try {
				byte[] fileContent = Files.readAllBytes(fileFirma.toPath());
				return Base64.getEncoder().encodeToString(fileContent);
			} catch (IOException e) {
				log.error("Se capturó una excepción: ", e);
//				WriteException.escribirExcepcion(getMessageException(e));
				throw new BusinessException("Error al leer el archivo de firma: " + e.getMessage());
			}
		} else {
			return obtenerBase64Default();
		}
	}

	private String obtenerBase64Default() {
		return "iVBORw0KGgoAAAANSUhEUgAAAMQAAABdCAIAAAB1rV63AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAQWSURBVHhe7dTZdttIDEDB/P9Pe46TckZJJKBpNcleUG9jXZK9IPPjo5ROaphKNzVMpZsaptJNDVPp5skw/fjiv8v2DEQ2En//7KEHfihbMgQP/PBMPky/KcoGXPkzimcODNMvurIo1/ya7pknv3koJC0LcbUh6Qsvf/Z0Rl2m5SIz6lA2a23UZSouL6Nu0Jp6cUhahufCQtIjjj3jOyFpGY8byqiP+9YAtlGXAbiSjPq73hjDNupyE9cQkr6tw4usKCQtV3HuGXUn/aaygbScyVln1F31ns026tKVww1Jz3HW2609JC3vcZoZ9ZlOHtUG0nKcE8yoz3fJwLZRlwaOLCS90IVj20ZdnnFGGfXl7pjfBtLyxblk1De5b4obSPfmLELSu909y23UO7HzjHoMwwx1A+nq7DajHslgo91AuiI7DEmHNOSAt1HPz34y6oGNu0RHmFHPyR4y6uHNMO8NpPOw7pB0HvNMfQPpwCw0o57NZOt22Bn1SKwso57TlKt38Bn13awmJJ3c5P8UGkgv5/MZ9RJW2IxrCUkv4ZMZ9ULW2ZIryqjP4Rsh6YpW/PfRQNqJl2bU61p2hy4wJH2DF2XUq1t8ny4zoz7CkyHpNrbYsLvNqEPSjHoze23bVYek//BzRr2lHTfv2kPSn/wpJN3bvqdgCt7mdWXnYfrNUBzn+fKlTgQD0sAD5R91NJ+MSTOPlT/tfi6m41u8onzZ90RMxNu8rmw4TEYgo/7Jn0LSvW10Cq49o/6HnzPqLW2xefcckmbUGfVmVt62i82oD/JwSLqNNTfsMjPqN3hRSLqB1bbqAkPSfrw3o17XIjt0XRn1aXwmJF3R9HtzRRn1JXwyJF3LxLtyLSHpHawgo17CfJtxCRn13awmo57cTNtw8Bn1YCwuJJ3WHBtw2CHp2Kw1JJ3Q0Et3uhn1PKw7o57HoCt2nBn1tGwjJJ3BcGt1hCHpKuwqJB3bKKt0Zhn1iuwwox7S/YtzSBn16uw2ox7MnctyMCHpfuw/JB3GDQtyEhn13pxFSDqAS5di9xl1+eJcMur7XLQC2w1JywuOKaO+w7nftr+MurRxaiHptc76qj1l1OU4J5hRX6L/x2wiJC1vc6AZ9cm6fcaqM+rSm/MNSU/T4QNWmlGXMznrkPQEb73a6kLSciFHn1H38503WktGXW7iGjLqHo69y/cz6jIGtxKSvqf1Lb4ZkpYhuaSQ9LuS530koy7Dc2EZ9UEvH/PWjLrMxv2FpM2ePOBNIWmZnOsMSRv8n3o0oy4LcbUZ9WufhTbz64GyKtecUT+jCAjLNlx8SPonvz0lKVsyBK/pHvjhL34s2zMQzygefP7JjzVD5TUj8sAPD2qAygHm6MX/d2qYSjc1TKWbGqbSTQ1T6eTj4z9gLrSsMNSC4AAAAABJRU5ErkJggg==";
	}
}
