<?php
namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\dto\input\InputCreateSalarie;
use charly\core\service\interfaces\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use InvalidArgumentException;

class PostSalarie extends AbstractAction
{
    protected UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $data = $rq->getParsedBody();

            // Validation des données d'entrée
            if (empty($data['nom']) || !is_string($data['nom'])) {
                return JsonRenderer::render($rs, 400, ['error' => 'Le champ "nom" est obligatoire et doit être une chaîne de caractères.']);
            }

            if (empty($data['competences']) || !is_array($data['competences'])) {
                return JsonRenderer::render($rs, 400, ['error' => 'Le champ "competences" doit être un tableau d\'objets.']);
            }

            foreach ($data['competences'] as $competence) {
                if (empty($competence['id']) || !is_int($competence['id'])) {
                    return JsonRenderer::render($rs, 400, ['error' => 'Chaque compétence doit avoir un champ "id" de type entier.']);
                }
                if (empty($competence['note']) || !is_int($competence['note']) || $competence['note'] < 1 || $competence['note'] > 5) {
                    return JsonRenderer::render($rs, 400, ['error' => 'Le champ "note" doit être un nombre entre 1 et 5.']);
                }
            }

            // Création du salarié
            $this->userService->createSalarie(new InputCreateSalarie($data['nom'], $data['competences']));

            return JsonRenderer::render($rs, 200);

        } catch (InvalidArgumentException $e) {
            // Erreur de validation ou d'argument
            return JsonRenderer::render($rs, 400, ['error' => $e->getMessage()]);
        } catch (\Exception $e) {
            // Autres erreurs imprévues
            return JsonRenderer::render($rs, 500, ['error' => 'Erreur interne du serveur.']);
        }
    }
}
